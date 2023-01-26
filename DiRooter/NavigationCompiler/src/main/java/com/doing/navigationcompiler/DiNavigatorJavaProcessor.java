package com.doing.navigationcompiler;

import com.doing.navigatorannotation.Destination;
import com.doing.navigatorannotation.DestinationJava;
import com.google.auto.service.AutoService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
public class DiNavigatorJavaProcessor extends AbstractProcessor {

    private TypeElement typeElement;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(Constant.TAG + " >>>> java process: " + annotations.size());

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(DestinationJava.class);
        if (elements.size() < 1) {
            return false;
        }

        System.out.println();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
            NestingKind nestingKind = typeElement.getNestingKind();
            Name simpleName = typeElement.getSimpleName();
            Name qualifiedName = typeElement.getQualifiedName();
            TypeMirror superclass = typeElement.getSuperclass();
            List<? extends TypeParameterElement> typeParameters = typeElement.getTypeParameters();
            DestinationJava annotation = typeElement.getAnnotation(DestinationJava.class);
            String pageUrl = annotation.pageUrl();


            System.out.println(Constant.TAG + " >>> java SimpleName: " + simpleName);
            System.out.println(Constant.TAG + " >>> java QualifiedName: " + qualifiedName);
            System.out.println(Constant.TAG + " >>> java NestingKind: " + nestingKind.name());
            System.out.println(Constant.TAG + " >>> java Interfaces: " + Arrays.toString(interfaces.toArray()));
            System.out.println(Constant.TAG + " >>> java SuperClass: " + superclass.toString());
            System.out.println(Constant.TAG + " >>> java TypeParameters: " + Arrays.toString(typeParameters.toArray()));
            System.out.println();
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(Constant.TAG + ">>>> java getSupportedAnnotationTypes : "
                + Destination.class.getCanonicalName());
        return Collections.singleton(DestinationJava.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println(Constant.TAG + " >>>> java getSupportedSourceVersion : "
                + SourceVersion.latestSupported());
        return SourceVersion.latestSupported();
    }
}
