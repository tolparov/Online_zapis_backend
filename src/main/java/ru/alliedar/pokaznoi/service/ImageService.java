package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.domain.task.Task;
import ru.alliedar.pokaznoi.domain.task.TaskImage;

public interface ImageService {

    String upload(TaskImage image);
}
