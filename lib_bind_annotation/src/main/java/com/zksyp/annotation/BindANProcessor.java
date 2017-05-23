package com.zksyp.annotation;

import com.google.auto.service.AutoService;
import com.zksyp.viewInject.ViewBindInjector;
import com.zksyp.viewInject.ViewBindInjector2;
import com.zksyp.viewInject.ViewInfo;

import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/11
 * Time:下午3:02
 * Desc:
 */

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.zksyp.annotation.BindView3", "com.zksyp.annotation.BindView4"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BindANProcessor extends AbstractProcessor {


    private Filer filer;
    private Elements elementUtils;
    private Messager messager;
    private Types typeUtils;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(BindView3.class.getCanonicalName());
        annotations.add(BindView4.class.getCanonicalName());
        return annotations;

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        messager = env.getMessager();
        typeUtils = env.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<String, ViewBindInjector> targetViewMap3 = findAndParseTargets3(roundEnv);
        for (String key : targetViewMap3.keySet()) {
            ViewBindInjector bindInjector = targetViewMap3.get(key);

            try {
                JavaFileObject jfo = filer.createSourceFile(bindInjector.getClassFullName()
                        , bindInjector.getTypeElement());
                Writer w = jfo.openWriter();
                w.write(bindInjector.brewJava());
                w.flush();
                w.close();
            } catch (Exception e) {
                error(bindInjector.getTypeElement(), "Unable to bind for type %s: %s"
                        , bindInjector.getTypeElement(), e.getMessage());
            }
        }

        Map<String, ViewBindInjector2> targetViewMap4 = findAndParseTargets4(roundEnv);
        for (String key : targetViewMap4.keySet()) {
            ViewBindInjector2 bindInjector = targetViewMap4.get(key);

            try {
                JavaFileObject jfo = filer.createSourceFile(bindInjector.getClassFullName()
                        , bindInjector.getTypeElement());
                Writer w = jfo.openWriter();
                w.write(bindInjector.brewJava());
                w.flush();
                w.close();
            } catch (Exception e) {
                error(bindInjector.getTypeElement(), "Unable to bind for type %s: %s"
                        , bindInjector.getTypeElement(), e.getMessage());
            }
        }

        return true;
    }

    private Map<String, ViewBindInjector2> findAndParseTargets4(RoundEnvironment env) {
        Map<String, ViewBindInjector2> targetViewMap = new LinkedHashMap<>();

        for (Element ele : env.getElementsAnnotatedWith(BindView4.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "ele = " + ele);
            messager.printMessage(Diagnostic.Kind.NOTE, "eleType = " + ele.asType());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleKind = " + ele.getKind());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleSimpleName = " + ele.getSimpleName());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleEnclosing = " + ele.getEnclosingElement());


            TypeElement classElement = (TypeElement) ele.getEnclosingElement();
            messager.printMessage(Diagnostic.Kind.NOTE, "classEle = " + classElement);

            PackageElement packageElement = elementUtils.getPackageOf(classElement);
            messager.printMessage(Diagnostic.Kind.NOTE, "packageEle = " + packageElement);

            String fqClassName = classElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "fqClassName = " + fqClassName);

            String packageName = packageElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName = " + packageName);

            String className = getClassName(classElement, packageName);
            messager.printMessage(Diagnostic.Kind.NOTE, "className = " + className);

            VariableElement varEle = (VariableElement) ele;
            messager.printMessage(Diagnostic.Kind.NOTE, "varEle = " + varEle);

            int id = varEle.getAnnotation(BindView3.class).value();
            String fieldName = varEle.getSimpleName().toString();
            String fieldType = varEle.asType().toString();

            messager.printMessage(Diagnostic.Kind.NOTE,
                    "annotation field : fieldName = " + fieldName + " , id = " + id
                            + " , fieldType = " + fieldType);
            ViewBindInjector2 bindInjector = targetViewMap.get(fqClassName);

            if (bindInjector == null) {
                bindInjector = new ViewBindInjector2(packageName, className);
                targetViewMap.put(fqClassName, bindInjector);
                bindInjector.setTypeElement(classElement);
            }

            bindInjector.putViewInfo(id, new ViewInfo(id, fieldName, fieldType));
        }

        return targetViewMap;
    }

    private Map<String, ViewBindInjector> findAndParseTargets3(RoundEnvironment env) {
        Map<String, ViewBindInjector> targetViewMap = new LinkedHashMap<>();

        for (Element ele : env.getElementsAnnotatedWith(BindView3.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "ele = " + ele);
            messager.printMessage(Diagnostic.Kind.NOTE, "eleType = " + ele.asType());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleKind = " + ele.getKind());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleSimpleName = " + ele.getSimpleName());
            messager.printMessage(Diagnostic.Kind.NOTE, "eleEnclosing = " + ele.getEnclosingElement());


            TypeElement classElement = (TypeElement) ele.getEnclosingElement();
            messager.printMessage(Diagnostic.Kind.NOTE, "classEle = " + classElement);

            PackageElement packageElement = elementUtils.getPackageOf(classElement);
            messager.printMessage(Diagnostic.Kind.NOTE, "packageEle = " + packageElement);

            String fqClassName = classElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "fqClassName = " + fqClassName);

            String packageName = packageElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName = " + packageName);

            String className = getClassName(classElement, packageName);
            messager.printMessage(Diagnostic.Kind.NOTE, "className = " + className);

            VariableElement varEle = (VariableElement) ele;
            messager.printMessage(Diagnostic.Kind.NOTE, "varEle = " + varEle);

            int id = varEle.getAnnotation(BindView3.class).value();
            String fieldName = varEle.getSimpleName().toString();
            String fieldType = varEle.asType().toString();

            messager.printMessage(Diagnostic.Kind.NOTE,
                    "annotation field : fieldName = " + fieldName + " , id = " + id
                            + " , fieldType = " + fieldType);
            ViewBindInjector bindInjector = targetViewMap.get(fqClassName);

            if (bindInjector == null) {
                bindInjector = new ViewBindInjector(packageName, className);
                targetViewMap.put(fqClassName, bindInjector);
                bindInjector.setTypeElement(classElement);
            }

            bindInjector.putViewInfo(id, new ViewInfo(id, fieldName, fieldType));
        }

        return targetViewMap;
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
