package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.task.TaskImage;
import ru.alliedar.pokaznoi.web.dto.task.TaskImageDto;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}
