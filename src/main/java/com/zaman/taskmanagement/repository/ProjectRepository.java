package com.zaman.taskmanagement.repository;


import com.zaman.taskmanagement.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    Project findFirstByNameAndDeletedFalse(String name);
//    Project findFirstByIdAndDeletedFalse(Integer name);

//    Project findFirstById (Integer Id);
    Project findFirstByIdAndDeletedFalse (Integer Id);
    Project findFirstByIdAndProjectOwnerAndDeletedFalse(Integer id,Long projectOwner);
    List<Project> findAllByProjectOwnerAndDeletedFalse(Long projectgetAllProjects);

}
