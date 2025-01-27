package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void findById_ShouldReturnTeacher_WhenTeacherExists() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // WHEN
        Teacher result = teacherService.findById(1L);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findAll_ShouldReturnAllTeachers() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));

        // WHEN
        List<Teacher> result = teacherService.findAll();

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }
} 