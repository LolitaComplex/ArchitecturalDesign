package com.doing.navigationcompiler


//@AutoService(Processor::class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//class DiNavigatorProcessor : AbstractProcessor(){
//
//    override fun process(annotations: MutableSet<out TypeElement>?,
//        roundEnv: RoundEnvironment?): Boolean {
//
//        println("Doing DiNavigator processor process")
//        val elementsSet = roundEnv?.getElementsAnnotatedWith(Destination::class.java)
//        if (elementsSet != null && elementsSet.isNotEmpty()) {
//            elementsSet.forEach { element ->
//                val typeElement = element as TypeElement
//
//                val className = typeElement.qualifiedName.toString()
//
//                println("Doing DiNavigatorProcessor processor className: $className")
//            }
//        }
//        println("Doing DiNavigator processor process")
//        return false
//    }
//
//}