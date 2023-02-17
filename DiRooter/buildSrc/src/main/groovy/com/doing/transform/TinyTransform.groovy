import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import sun.tools.java.ClassFile

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class TinyTransform extends Transform {

    private ClassPool classPool = ClassPool.getDefault()

    TinyTransform(Project project) {
        // 需要把android.jar中Android内置SDK添加到 classPool的类搜索路径上去
        classPool.appendClassPath(project.android.bootClassPath[0].toString())
        classPool.importPackage("android.os.Bundle")
        classPool.importPackage("android.widget.Toast")
        classPool.importPackage("android.app.Activity")
    }

    @Override
    String getName() {
        return "TinyPngTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        def provider = transformInvocation.outputProvider

        transformInvocation.inputs.each {  input ->
            input.directoryInputs.each {  dirInput ->
                // 修改文件
                handleDirectory(dirInput.file)

                def dest = provider.getContentLocation(dirInput.name, dirInput.contentTypes,
                        dirInput.scopes, Format.DIRECTORY)
                // 拷贝文件
                FileUtils.copyDirectory(dirInput.file, dest)
            }

            input.jarInputs.each {jarInput ->
                // 修改jar包输出到新文件srcJar
                def srcJar = handleJar(jarInput.file)

                def name = jarInput.name
                // jar包路径的md5值
                def md5 = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (name.endsWith(".jar")) {
                    name = name.substring(0, name.length() - 4)
                }
                def dest = provider.getContentLocation(md5 + name, jarInput.contentTypes,
                    jarInput.scopes, Format.JAR)
                FileUtils.copyFile(srcJar, dest)
            }
        }

        classPool.clearImportedPackages()
    }

    private void handleDirectory(File dir) {
        classPool.appendClassPath(dir.absolutePath)

        if (!dir.isDirectory()) {
            return
        }

        dir.eachFileRecurse {file ->
            def filePath = file.absolutePath
            if (isShouldModifyClass(filePath)) {
                def inputStream = FileInputStream(file)
                def ctClass = modifyClass(inputStream)
                ctClass.writeFile(dir.name)
                // 从CLassPool里释放掉
                ctClass.detach()
            }
        }
    }

    private File handleJar(File jar) {
        classPool.appendClassPath(jar.absolutePath)
        def inputJar = new JarFile(jar)
        def enumeration = inputJar.entries()

        // 输出到Jar的父目录层级
        def outputJar = new File(jar.parentFile, "temp_${jar.name}")
        if (outputJar.exists()) {
            outputJar.delete()
        }
        def jarOutputStream = new JarOutputStream(new BufferedOutputStream
                (new FileOutputStream(outputJar)))
        while (enumeration.hasMoreElements()) {
            def element = enumeration.nextElement()
            def elementName = element.name

            def entry = new JarEntry(elementName)
            jarOutputStream.putNextEntry(entry)


            def inputStream = inputJar.getInputStream(entry)
            // 不是目标文件就直接拷贝即可
            if (!isShouldModifyClass(elementName)) {
                jarOutputStream.write(IOUtils.toByteArray(inputStream))
                inputStream.close()
                continue
            }

            def ctClass = modifyClass(inputStream)
            def byteCode = ctClass.toBytecode()
            ctClass.detach()
            inputStream.close()
            jarOutputStream.write(byteCode)
            jarOutputStream.flush()
        }
        inputJar.close()
        jarOutputStream.closeEntry()
        jarOutputStream.flush()
        jarOutputStream.close()
        return outputJar
    }

    private CtClass modifyClass(InputStream inputStream) {
        def classFile = new ClassFile(new DataInputStream(new BufferedInputStream(inputStream)))
        def ctClass = classPool.get(classFile.name)
        // 为了让ClassLoader加载这个类不存缓存，重新加载
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }

        def ctBundle = classPool.get("android.os.Bundle")
        def ctParams = Arrays.asList(ctBundle).toArray()
        def ctMethod = ctClass.getDeclaredMethod("onCreate", ctParams)
        ctMethod.insertAfter("Toast.makeText(this, \""
                + classFile.name + "\", Toast.LENGTH_SHORT).show()")
        return ctClass
    }

    private static boolean isShouldModifyClass(String path) {
        return path.contains("com/doing/app")
                && path.endsWith("Activity.class")
                && !path.contains("R.class")
                && !path.contains('$')
                && !path.contains("BuildConfig.class")
                && !path.contains('R$')
    }
}