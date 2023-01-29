package atlg.g56055.mentoring;

import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Mentoring {
    public Mentoring() {
    }

    public static void main(String[] args) throws RepositoryException {
        Mentoring mentoring = new Mentoring();
        mentoring.checkPath();
        try {
            ConfigManager.getInstance().load();
        } catch (IOException e) {
            System.out.println("Chargement de la configuration impossible " + e.getMessage());
        }

        String author = ConfigManager.getInstance().getProperties("app.author");
        System.out.println("Auteur : " + author);
        StudentDto std = new StudentDto(56, "b", "e");
        StudentDto std2 = new StudentDto(57, "e", "c");
        StudentDto std3 = new StudentDto(42, "Ethan", "Fox");
        StudentRepository stdRepo = new StudentRepository();
        stdRepo.add(std);
        stdRepo.add(std2);
        stdRepo.add(std3);
        System.out.println(stdRepo.get(55002).toString());
        List<StudentDto> t = stdRepo.getAll();
        for (StudentDto it : t) {
            System.out.println(it.toString());
        }
    }

    public void checkPath() {
        System.out.println("Chemin courant \t" + new File(".").getAbsolutePath());
        System.out.println("Chemin classe \t" +
                this.getClass().getResource(".").getPath());
        System.out.println("Chemin jar \t" + new
                File(getClass().getClassLoader().getResource(".").getFile()));
    }
}
