package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.AttendanceCr;
import uz.pdp.learning_center_full.dto.request.LessonCR;
import uz.pdp.learning_center_full.dto.response.LessonResponse;
import uz.pdp.learning_center_full.entity.CourseEntity;
import uz.pdp.learning_center_full.entity.GroupEntity;
import uz.pdp.learning_center_full.entity.LessonEntity;
import uz.pdp.learning_center_full.entity.enums.GroupStatus;
import uz.pdp.learning_center_full.entity.enums.LessonStatus;
import uz.pdp.learning_center_full.exception.DataNotFoundException;
import uz.pdp.learning_center_full.repository.CourseRepository;

import uz.pdp.learning_center_full.repository.GroupRepository;
import uz.pdp.learning_center_full.repository.LessonRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final AttendanceService attendanceService;

    public LessonResponse create(LessonCR lessonCR) {
        LessonEntity lessonEntity = modelMapper.map(lessonCR, LessonEntity.class);
        return modelMapper.map(lessonRepository.save(lessonEntity),LessonResponse.class);
    }

    public List<LessonResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<LessonEntity> lessonEntityList = lessonRepository.findAll(pageable).getContent();
        return modelMapper.map(lessonEntityList, new TypeToken<List<LessonResponse>>(){}.getType());
    }
    public LessonResponse findById(UUID id) {
        return modelMapper.map(lessonRepository.findById(id).get(), LessonResponse.class);
    }
    public List<LessonResponse> getLesson(UUID groupId) {
        List<LessonEntity> lessonEntityList = lessonRepository.findLessonsByGroupId(groupId);
        return modelMapper.map(lessonEntityList, new TypeToken<List<LessonResponse>>(){}.getType());
    }
    public ResponseEntity<LessonResponse> startLesson(UUID lessonId, UUID groupId) {
        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new DataNotFoundException("Lesson nod found by this Id " + lessonId));
        lessonEntity.setLessonStatus(LessonStatus.STARTED);
        lessonEntity.setGroupId(groupId);
        lessonRepository.save(lessonEntity);
        return ResponseEntity.ok(modelMapper.map(lessonEntity,LessonResponse.class));
    }
    public ResponseEntity<String> finishLesson(List<AttendanceCr> attendanceCrList) {
        LessonEntity lessonEntity = lessonRepository.findById(attendanceCrList.get(0).getLessonId())
                .orElseThrow(() -> new DataNotFoundException("Lesson not found! " + attendanceCrList));
        lessonEntity.setLessonStatus(LessonStatus.FINISHED);
        lessonRepository.save(lessonEntity);
        GroupEntity group = groupRepository.findById(lessonEntity.getGroupId()).get();
        CourseEntity course = courseRepository.findById(group.getCourseId()).get();

        if(lessonEntity.getLessonNumber() == 12 && course.getModule() == group.getModule()){
            group.setStatus(GroupStatus.FINISHED);
            groupRepository.save(group);
            return ResponseEntity.ok("Course finished!");
        } else if (lessonEntity.getLessonNumber() == 12) {
            group.setModule(group.getModule()+1);
            groupRepository.save(group);
        }
        return attendanceService.createAttendances(attendanceCrList);
    }




}
