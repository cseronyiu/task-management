package com.zaman.taskmanagement.repository;

import com.zaman.taskmanagement.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findFirstById (Integer Id);
//    Task findFirstByProject_IdAndDescription(Integer projectId,String description);
    Task findFirstByProject_IdAndDescriptionAndDeletedFalse(Integer projectId,String description);

    List<Task> findAllByProject_IdAndDeletedFalse(Integer projectId);
    List<Task>findAllByProject_IdInAndDueDateBeforeAndDeletedFalse(List<Integer> ids,Date currentDate);
    List<Task>findAllByProject_IdInAndStatusAndDeletedFalse(List<Integer> ids,String status);
    List<Task>findAllByProject_IdInAndDeletedFalse(List<Integer> ids);
//    List<Task>findAllByProject_IdAndIsDeletedFalse(Integer projectId);
}
