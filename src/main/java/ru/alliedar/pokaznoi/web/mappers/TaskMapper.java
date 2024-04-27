package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.task.Task;
import ru.alliedar.pokaznoi.web.dto.task.TaskDto;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
