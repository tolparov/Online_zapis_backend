package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.domain.task.Task;
import ru.alliedar.pokaznoi.domain.task.TaskImage;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);
}
