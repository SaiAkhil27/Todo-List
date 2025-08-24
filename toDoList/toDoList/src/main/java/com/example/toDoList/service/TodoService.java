package com.example.toDoList.service;


import com.example.toDoList.controller.TodoController;
import com.example.toDoList.dto.TodoDTO;
import com.example.toDoList.entity.Todo;
import com.example.toDoList.exception.ResourceNotFoundException;
import com.example.toDoList.repository.TodoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.openpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
  @Autowired
  private TodoRepository todoRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private static final Logger logger = LoggerFactory.getLogger(TodoController.class);


  public List<TodoDTO> getAllTodos(){
    return todoRepository.findAll()
            .stream()
            .map(todo -> modelMapper.map(todo,TodoDTO.class))
            .collect(Collectors.toList());
  }

  public TodoDTO getToDoById(Long id){
    Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given id is not present" + id));
    return modelMapper.map(todo,TodoDTO.class);
  }

  public TodoDTO createNewTodo(TodoDTO todoDTO){
    Todo todo = modelMapper.map(todoDTO,Todo.class);
    todoRepository.save(todo);
    return modelMapper.map(todo,TodoDTO.class);
  }

  public TodoDTO updateTheTodo(Long id,TodoDTO todoDTO){
    Todo existong = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given id is not present" + id));

    existong.setTitle(todoDTO.getTitle());
    existong.setDescription((todoDTO.getDescription()));
    existong.setCompleted(todoDTO.isCompleted());
Todo updated = todoRepository.save(existong);
    return modelMapper.map(updated,TodoDTO.class);
  }

  public void deleteTodo(Long id) {
    Todo existing = todoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));
    todoRepository.delete(existing);
  }

  public void deletAll() {
    todoRepository.deleteAll();

    jdbcTemplate.execute("ALTER TABLE Todoooss AUTO_INCREMENT = 1");

  }


}
