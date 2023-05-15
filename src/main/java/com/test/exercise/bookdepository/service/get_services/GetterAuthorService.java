package com.test.exercise.bookdepository.service.get_services;

import com.test.exercise.bookdepository.dto.AuthorDTO;
import com.test.exercise.bookdepository.exception.AuthorException;
import com.test.exercise.bookdepository.model.Author;
import com.test.exercise.bookdepository.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO: добавить логирование
@Service
public class GetterAuthorService implements GetEntity<AuthorDTO>{
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public GetterAuthorService(AuthorRepository authorRepository,
                               ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод в котором находят автора по его идентификатору
     *
     * @param authorId идентификатор автора
     * @return Транспортную сущность автора
     */
    @Override
    public AuthorDTO getById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    throw new AuthorException("Автор под id " + authorId + " не найден");
                });
        return modelMapper.map(author, AuthorDTO.class);
    }

    /**
     * Метод в котором находят автора по его фамилии
     *
     * @param authorSureName     имя автора
     * @return Транспортную сущность автора
     */
    @Override
    public AuthorDTO getByName(String authorSureName) {
        Author author = authorRepository.findAuthorBySureName(authorSureName)
                .orElseThrow(() -> {
                    throw new AuthorException(String.format("Автор по имени %s %s не найден", authorSureName));
                });
        return modelMapper.map(author, AuthorDTO.class);
    }

    /**
     * Метод в котором находят автора по его имени и фамилии
     *
     * @param authorName     имя автора
     * @param authorSureName фамилия автора
     * @return Транспортную сущность автора
     */
    public AuthorDTO getByName(String authorName, String authorSureName) {
        Author author = authorRepository.findAuthorByNameAndSureName(authorName, authorSureName)
                .orElseThrow(() -> {
                    throw new AuthorException(String.format("Автор по имени %s %s не найден", authorName, authorSureName));
                });
        return modelMapper.map(author, AuthorDTO.class);
    }

    /**
     * Метод который получает всех авторов вне зависимости от книги
     *
     * @return списко авторов
     */
    @Override
    public List<AuthorDTO> getAll() {
        List<AuthorDTO> authorDTOList = null;
        try {
            authorDTOList = authorRepository.findAll().stream()
                    .map(author -> modelMapper.map(author, AuthorDTO.class))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new AuthorException("Список авторов пуст");
        }
        if (authorDTOList == null || authorDTOList.isEmpty()) {
            throw new AuthorException("Список авторов пуст");
        }
        return authorDTOList;
    }
}
