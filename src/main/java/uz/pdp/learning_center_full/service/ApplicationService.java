package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.learning_center_full.dto.request.ApplicationCr;
import uz.pdp.learning_center_full.dto.response.ApplicationResponse;
import uz.pdp.learning_center_full.entity.ApplicationEntity;
import uz.pdp.learning_center_full.entity.CourseEntity;
import uz.pdp.learning_center_full.entity.enums.ApplicationStatus;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.ApplicationRepository;
import uz.pdp.learning_center_full.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.pdp.learning_center_full.entity.enums.ApplicationStatus.UNCHECKED;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationResponse create(ApplicationCr applicationCR) {
        Optional<CourseEntity> course = courseRepository.findById(applicationCR.getCourseId());
        if (course.isEmpty()) {
            throw new DataNotFoundException("course not found by this id " + applicationCR.getCourseId());
        } else if (applicationRepository.existsByEmail(applicationCR.getEmail()) &&
                applicationRepository.existsByCourseId(applicationCR.getCourseId())) {
            throw new DuplicateValueException("You have already sent application this course!");
        }
        ApplicationEntity applicationEntity = modelMapper.map(applicationCR, ApplicationEntity.class);
        applicationEntity.setStatus(UNCHECKED);
        return modelMapper.map(applicationRepository.save(applicationEntity), ApplicationResponse.class);
    }

    public List<ApplicationResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ApplicationEntity> applications = applicationRepository.findAll(pageable).getContent();
        return modelMapper.map(applications, new TypeToken<List<ApplicationResponse>>(){}.getType());
    }
    public ResponseEntity<ApplicationResponse> findById(UUID applicationId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new DataNotFoundException("Application not found with this id " + applicationId));
        return ResponseEntity.ok(modelMapper.map(applicationEntity,ApplicationResponse.class));
    }

    public ResponseEntity<ApplicationResponse> confirm(UUID applicationId) {
        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new DataNotFoundException("Application not found with this id " + applicationId));
        application.setStatus(ApplicationStatus.CHECKED);
        applicationRepository.save(application);
        return ResponseEntity.ok(modelMapper.map(application,ApplicationResponse.class));
    }

    public List<ApplicationResponse> getAllByStatus(int page, int size,ApplicationStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        List<ApplicationEntity> applications = applicationRepository.findAllByStatus(pageable,status).getContent();
        return modelMapper.map(applications, new TypeToken<List<ApplicationResponse>>(){}.getType());
    }
}
