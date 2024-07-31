package com.hg.product.utility.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {

    public record MyUser(String name, String lastName) implements Serializable {
    }

    public static void main(String[] args) {
        var fo = new FileOperation();
        List<String> names = List.of("tyler", "burden", "neovim");
        String fileName = "x.file";

//         save to file
        Path path = Paths.get(fileName);
        boolean result = fo.saveToFile(path, names);
        System.out.println("result = " + result);

        // read from the file
        List<String> line = fo.readFromFile(path);
        System.out.println("line = " + line);

        // write an object
        String objFileName = "obj.file";
        MyUser user = new MyUser("micheal", "douglas");
        Path pathObject = Paths.get(objFileName);
        boolean isSavedObject = fo.writeObjectToFile(pathObject, user);
        System.out.println("isSavedObject = " + isSavedObject);

        // read on object
        MyUser myUser = fo.readObject(pathObject);
        System.out.println("myUser = " + myUser);
    }

    private MyUser readObject(Path path) {
        try (ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            return (MyUser) oos.readObject();
        } catch (Exception exception) {
            System.out.println("read object exception = " + exception);
        }
        return null;
    }

    private boolean writeObjectToFile(Path path, MyUser user) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE)))) {
            oos.writeObject(user);
            oos.flush();
            return true;
        } catch (Exception exception) {
            System.out.println("inner exception ? = " + exception);
        }
        return false;
    }

    private List<String> readFromFile(Path path) {
        List<String> names = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line);
            }
        } catch (Exception exception) {
            System.out.println("readFile exception = " + exception);
        }
        return names;
    }

    private boolean saveToFile(Path path, List<String> in) {
        try (var bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bufferedWriter.write(String.join(" ", in));
            bufferedWriter.write(System.lineSeparator());
            return true;
        } catch (Exception exception) {
            System.out.println("saving exception = " + exception);
        }
        return false;
    }
}
