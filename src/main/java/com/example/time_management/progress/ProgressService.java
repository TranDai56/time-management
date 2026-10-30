package com.example.time_management.progress;

import com.example.time_management.task.Task;
import com.example.time_management.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final TaskRepository taskRepository;

    public ProgressService(
            ProgressRepository progressRepository,
            TaskRepository taskRepository) {
        this.progressRepository = progressRepository;
        this.taskRepository = taskRepository;
    }

    public Progress createProgress(Progress progress) {

        if (progress.getTask() == null ||
                progress.getTask().getId() == null) {
            throw new RuntimeException("Progress phải có task");
        }

        Task task = taskRepository.findById(
                progress.getTask().getId()
        ).orElseThrow(() ->
                new RuntimeException("Không tìm thấy task"));

        progress.setTask(task);

        validatePercentage(progress.getPercentage());

        return progressRepository.save(progress);
    }

    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    public Progress getProgressById(Long id) {
        return progressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy progress"));
    }

    public Progress getProgressByTask(Long taskId) {
        return progressRepository.findByTaskId(taskId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Task chưa có progress"));
    }

    public Progress updateProgress(
            Long id,
            Progress newProgress) {

        Progress progress = getProgressById(id);

        progress.setStatus(newProgress.getStatus());
        progress.setCompleted(newProgress.isCompleted());

        validatePercentage(newProgress.getPercentage());
        progress.setPercentage(newProgress.getPercentage());

        if (newProgress.isCompleted()) {
            progress.setPercentage(100);
        }

        return progressRepository.save(progress);
    }

    public void completeTask(Long id) {

        Progress progress = getProgressById(id);

        progress.setCompleted(true);
        progress.setStatus("COMPLETED");
        progress.setPercentage(100);

        progressRepository.save(progress);
    }

    public void deleteProgress(Long id) {
        Progress progress = getProgressById(id);
        progressRepository.delete(progress);
    }

    public long countAll() {
        return progressRepository.count();
    }

    public long countCompleted() {
        return progressRepository.findAll()
                .stream()
                .filter(Progress::isCompleted)
                .count();
    }

    public long countIncomplete() {
        return progressRepository.findAll()
                .stream()
                .filter(progress -> !progress.isCompleted())
                .count();
    }

    private void validatePercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new RuntimeException(
                    "Tiến độ phải nằm trong khoảng 0 đến 100");
        }
    }
}