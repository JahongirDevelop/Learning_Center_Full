package uz.pdp.learning_center_full.service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.CourseCr;
import uz.pdp.learning_center_full.dto.response.CourseResponse;
import uz.pdp.learning_center_full.entity.CourseEntity;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.exception.DuplicateValueException;
import uz.pdp.learning_center_full.repository.CourseRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public CourseResponse create(CourseCr courseCr){
        CourseEntity courseEntity = modelMapper.map(courseCr, CourseEntity.class);
        List<CourseEntity> courseEntities = courseRepository.findBySubject(courseCr.getSubject());
        for (CourseEntity entity : courseEntities) {
            if(!Objects.equals(null,entity) &&
                    Objects.equals(entity.getSubject(),courseEntity.getSubject()) &&
                    Objects.equals(entity.getDescription(),courseEntity.getDescription()) &&
                    Objects.equals(entity.getModule(),courseEntity.getModule())){
                throw new DuplicateValueException("Course already exist by these values!");
            }
        }
        return modelMapper.map(courseRepository.save(courseEntity), CourseResponse.class);
    }

    public ResponseEntity<CourseResponse> findById(UUID id) {
        CourseEntity courseEntity = modelMapper.map(courseRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Invalid value")),CourseEntity.class);
        return ResponseEntity.ok(modelMapper.map(courseEntity,CourseResponse.class));
    }

    public ResponseEntity<List<CourseResponse>> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<CourseEntity> all = courseRepository.findAll(pageable).getContent();
        return ResponseEntity.ok(modelMapper.map(all, new TypeToken<List<CourseResponse>>() {}.getType()));

    }


    public ResponseEntity<String> deleteById(UUID id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok("");
    }
}
