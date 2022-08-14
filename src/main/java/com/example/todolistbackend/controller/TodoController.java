package com.example.todolistbackend.controller;

import com.example.todolistbackend.dto.ResponseDTO;
import com.example.todolistbackend.dto.TodoDTO;
import com.example.todolistbackend.model.TodoEntity;
import com.example.todolistbackend.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(temporaryUserId);
            List<TodoEntity> entities = service.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";
        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(temporaryUserId);
        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(temporaryUserId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
