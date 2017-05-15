package com.zksyp.viewInject;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/11
 * Time:下午4:08
 * Desc:
 */

public class ViewBindInjector {

    private String packageName;
    private String targetClassName;
    private TypeElement typeElement;
    private String suffixClassName;
    public static final String SUFFIX = "$$BINDVIEW";
    private Map<Integer, ViewInfo> idViewMap = new HashMap<>();

    public ViewBindInjector(String packageName, String className) {
        this.packageName = packageName;
        this.targetClassName = className;
        this.suffixClassName = className + SUFFIX;
    }

    public String getClassFullName() {
        return packageName + "." + suffixClassName;
    }

    public void putViewInfo(int id, ViewInfo viewInfo) {
        idViewMap.put(id, viewInfo);
    }

    public String brewJava() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from BindViewTest. Do not modify!\n");
        builder.append("package").append(packageName).append(";\n\n");
        builder.append("import android.view.View;\n");
        builder.append("import com.example.viewInject.Finder;\n");
        builder.append("import com.zksyp.lib_bind.AbstractBinder;\n");
        builder.append('\n');

        builder.append("public class ").append(suffixClassName);
        builder.append("<T extends ").append(getTargetClassName()).append(">");
        builder.append(" implements AbstractBinder<T>");
        builder.append("{\n");

        builder.append(generateInjectMethod());
        builder.append("\n");
        builder.append("}\n");
        return builder.toString();
    }

    private String generateInjectMethod() {
        StringBuilder sb = new StringBuilder();

        sb.append(" @Override ").append("public void bind(final Finder finder, final T target, Object source) {\n");
        sb.append("  View view;\n");
        for (int key : idViewMap.keySet()) {
            ViewInfo viewInfo = idViewMap.get(key);
            sb.append("  view = ");
            sb.append("finder.findViewById(source , ");
            sb.append(viewInfo.getId()).append(" );\n");
            sb.append("target.").append(viewInfo.getName()).append(" = ");
            sb.append("finder.castView( view ").append(", ").append(viewInfo.getId()).append(" , \"");
            sb.append(viewInfo.getName() + " \" );");
        }
        sb.append(" }\n");
        return sb.toString();
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    private String getTargetClassName() {
        return targetClassName.replace("$", ".");
    }
}
