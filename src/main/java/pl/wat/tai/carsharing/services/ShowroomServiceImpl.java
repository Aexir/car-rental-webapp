package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.mappers.ShowroomMapper;
import pl.wat.tai.carsharing.repositories.ShowroomRepository;
import pl.wat.tai.carsharing.services.interfaces.ShowroomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {

    private final ShowroomRepository showroomRepository;
    private final ShowroomMapper showroomMapper;

    @Override
    public void addShowroom(ShowroomRequest showroomRequest) {
        showroomRepository.save(showroomMapper.requestToShowroom(showroomRequest));
    }

    @Override
    public void removeShowroom(String name) {
        showroomRepository.findAll().remove(showroomRepository.findByName(name));
    }

    @Override
    public List<ShowroomResponse> getAll() {
        return showroomRepository.findAll().stream().map(showroomMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ShowroomResponse get(String name)  {
        return showroomMapper.mapToResponse(showroomRepository.findByName(name));
    }
}
