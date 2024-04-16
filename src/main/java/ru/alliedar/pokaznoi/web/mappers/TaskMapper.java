package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.task.Task;
import ru.alliedar.pokaznoi.web.dto.task.TaskDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    Task toEntity(TaskDto dto);
}
