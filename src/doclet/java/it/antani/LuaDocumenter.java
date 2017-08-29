package it.antani;

import com.sun.javadoc.*;
import com.sun.tools.javadoc.ClassDocImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LuaDocumenter {
    public static boolean start(RootDoc root) {

        try {
            ClassDoc[] classes = root.classes();
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(getPath(root.options())));
            for(ClassDoc clazz : classes){
                for(AnnotationDesc ann : clazz.annotations()){
                    if(ann.annotationType().toString().equals("it.antani.cc.annotations.AcceptsTileEntity")){
                        writeClass(writer, clazz, ann);
                    }
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String getPath(String[][] options){
        for(String[] opt: options){
            if(opt[0].equals("-d")){
                return opt[1] + File.separator + "luaDoc.md";
            }
        }
        throw new RuntimeException("No -d param was provided");
    }

    public static int optionLength(String option) {
        if(option.equals("-d")) {
            return 2;
        }
        return 0;
    }

    public static boolean validOptions(String options[][],
                                       DocErrorReporter reporter) {
        return true;
    }


    private static void writeClass(BufferedWriter writer, ClassDoc clazz, AnnotationDesc ann) throws IOException{
        String tileEntity = ((ClassDocImpl)ann.elementValues()[0].value().value()).asClassDoc().name();
        writer.write("# " + tileEntity);
        writer.newLine();
        writer.write(clazz.commentText());
        writer.newLine();
        for (MethodDoc m : clazz.methods()){
            boolean lua = false;
            String method = "";
            for(AnnotationDesc mann : m.annotations()){
                if(mann.annotationType().toString().equals("it.antani.cc.annotations.LuaMethod")){
                    lua = true;
                    method = mann.elementValues()[0].value().value().toString();
                    break;
                }
            }
            if(lua){
                writer.write("## " + method);
                writer.newLine();
                writer.write(m.commentText());
                writer.newLine();
            }
        }
    }


}
