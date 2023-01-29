package atlg.g56055.mentoring;

import atlg.g56055.mentoring.dto.StudentDto;
import atlg.g56055.mentoring.exception.RepositoryException;
import atlg.g56055.mentoring.repository.Dao;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentDao implements Dao<StudentDto, Integer> {
    private Path path;
    private StudentDao() {
        String url = ConfigManager.getInstance().getProperties("file.url");
        path = Paths.get(url);
    }

    StudentDao(String url) {
        path = Paths.get(url);
    }

    private static class StudentDaoHolder {
        private static final StudentDao INSTANCE = new StudentDao();
    }

    public static StudentDao getInstance() {
        return StudentDaoHolder.INSTANCE;
    }

    @Override
    public void insert(StudentDto item) throws RepositoryException {
        if(item.getKey() == null){
            throw new RepositoryException(new IllegalArgumentException("Key not valid"));
        }
        try {
            Files.writeString(path, item.toString() + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if(key ==null){
            throw new RepositoryException(new IllegalArgumentException("Key Not valid"));
        }
        try {
            List<String> out = Files.lines(path).filter(line -> !line.split(",")[0]
                            .contains(Integer.toString(key)))
                    .collect(Collectors.toList());
            if (out.size() == Files.lines(path).count()) {
                throw new RepositoryException(new IOException("Absent du fichier " + key));
            }
            Files.write(path, out,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(StudentDto item) throws RepositoryException {
        if(item.getKey() == null){
            throw new RepositoryException(new IllegalArgumentException("Key not valid"));
        }
        delete(item.getKey());
        insert(item);
    }

    @Override
    public StudentDto get(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException(new IllegalArgumentException("Key not valid"));
        }
        try {
            StudentDto[] studentDto = new StudentDto[1];
            Files.lines(path).forEach(s -> {
                String[] student = s.split(",");
                if (Integer.parseInt(student[0]) == key) {
                    studentDto[0] = new StudentDto(Integer.parseInt(student[0]),
                            student[1], student[2]);
                }
            });

            return studentDto[0];
        } catch (IOException ex) {
            throw new RepositoryException(ex);

        }
    }

    @Override
    public List<StudentDto> getAll() throws RepositoryException {
        List<String> l = null;
        List<StudentDto> studentList = new ArrayList<>();
        try {
            l = Files.lines(path).toList();
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
        for (String item : l) {
            String[] temp = item.split(",");
            studentList.add(new StudentDto(Integer.parseInt(temp[0]), temp[1], temp[2]));
        }
        return studentList;
    }
}
