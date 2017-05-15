package com.zksyp.annotation;

import com.zksyp.viewInject.ViewBindInjector;
import com.zksyp.viewInject.ViewInfo;

import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/11
 * Time:下午3:02
 * Desc:
 */


public class BindANProcessor extends AbstractProcessor {


    private Filer filer;
    private ProcessingEnvironment processingEnv;


    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        filer = env.getFiler();
        processingEnv = env;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<String, ViewBindInjector> targetViewMap = findAndParseTargets(roundEnv);

        for (String key : targetViewMap.keySet()) {
            ViewBindInjector bindInjector = targetViewMap.get(key);

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

    private Map<String, ViewBindInjector> findAndParseTargets(RoundEnvironment env) {
        Map<String, ViewBindInjector> targetViewMap = new LinkedHashMap<>();

        for (Element ele : env.getElementsAnnotatedWith(BindView3.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ele = " + ele);

            TypeElement classElement = (TypeElement) ele;
            PackageElement packageElement = (PackageElement) ele.getEnclosingElement();

            String fqClassName = classElement.getQualifiedName().toString();
            String packageName = packageElement.getQualifiedName().toString();
            String className = getClassName(classElement, packageName);

            VariableElement varEle = (VariableElement) ele;
            int id = varEle.getAnnotation(BindView3.class).value();
            String fieldName = varEle.getSimpleName().toString();
            String fieldType = varEle.asType().toString();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
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
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
