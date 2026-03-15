package com.santy.taskorg.repository;

import com.santy.taskorg.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
