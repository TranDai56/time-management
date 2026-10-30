package com.example.time_management.progress;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public Progress createProgress(
            @RequestBody Progress progress) {

        return progressService.createProgress(progress);
    }

    @GetMapping
    public List<Progress> getAllProgress() {
        return progressService.getAllProgress();
    }

    @GetMapping("/{id}")
    public Progress getProgressById(
            @PathVariable Long id) {

        return progressService.getProgressById(id);
    }

    @GetMapping("/task/{taskId}")
    public Progress getProgressByTask(
            @PathVariable Long taskId) {

        return progressService.getProgressByTask(taskId);
    }

    @PutMapping("/{id}")
    public Progress updateProgress(
            @PathVariable Long id,
            @RequestBody Progress progress) {

        return progressService.updateProgress(id, progress);
    }

    @PutMapping("/{id}/complete")
    public void completeTask(
            @PathVariable Long id) {

        progressService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProgress(
            @PathVariable Long id) {

        progressService.deleteProgress(id);
    }

    @GetMapping("/statistics/total")
    public long countAll() {
        return progressService.countAll();
    }

    @GetMapping("/statistics/completed")
    public long countCompleted() {
        return progressService.countCompleted();
    }

    @GetMapping("/statistics/incomplete")
    public long countIncomplete() {
        return progressService.countIncomplete();
    }
}