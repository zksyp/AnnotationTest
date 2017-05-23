package com.zksyp.viewInject;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

/**
 * Created with Android Studio.
 * User:kaishen
 * Date:2017/5/23
 * Time:上午9:57
 * Desc:
 */

public class ViewBindInjector2 {
    private String packageName;
    private String targetClassName;
    private TypeElement typeElement;
    private String suffixClassName;
    public static final String SUFFIX = "$$BINDVIEW";
    private Map<Integer, ViewInfo> idViewMap = new HashMap<>();

    public ViewBindInjector2(String packageName, String className) {
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
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import android.view.View;\n");
        builder.append("import com.zksyp.lib_bind.Utils;\n");
        builder.append('\n');
        builder.append("public class ").append(suffixClassName);
        builder.append("  private ").append(getTargetClassName()).append(" target;\n\n");
        builder.append(generateInjectMethod());
        builder.append("\n");
        builder.append("}\n");
        return builder.toString();
    }

    /**
     * 该方法就是按照常用的Activity格式去自己拼出.java文件的内容
     *
     * @return
     */
    private String generateInjectMethod() {
        StringBuilder sb = new StringBuilder();

        sb.append("  public ").append(suffixClassName).append("(").append(getTargetClassName())
                .append(" target").append(") {\n").append("   this(target, target.getWindow().getDecorView());\n}\n\n");

        sb.append("  public ").append(suffixClassName).append("(").append(getTargetClassName())
                .append(" target").append(", View source").append(") {\n").append("   this.target = target;\n\n");

        for (int key : idViewMap.keySet()) {
            ViewInfo viewInfo = idViewMap.get(key);
            sb.append("   target.").append(viewInfo.getName()).append(" = ")
                    .append("Utils.findRequiredViewAsType(source, ").append(viewInfo.getId())
                    .append(", \"field ").append("\'").append(viewInfo.getName()).append("'\'\"")
                    .append(", ").append(viewInfo.getType()).append(".class").append(");\n")
                    .append("}\n\n");
        }
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
