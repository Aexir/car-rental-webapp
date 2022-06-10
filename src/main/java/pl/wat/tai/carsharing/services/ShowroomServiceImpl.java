package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.mappers.ShowroomMapper;
import pl.wat.tai.carsharing.repositories.LocationRepository;
import pl.wat.tai.carsharing.repositories.ShowroomRepository;
import pl.wat.tai.carsharing.services.interfaces.ShowroomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {

    private final ShowroomRepository showroomRepository;
    private final LocationRepository locationRepository;
    private final ShowroomMapper showroomMapper;

    @Override
    public void addShowroom(ShowroomRequest showroomRequest) {
        if (showroomRepository.findByName(showroomRequest.getName()) == null)
        {
            showroomRepository.save(showroomMapper.requestToShowroom(showroomRequest));
        } else {
            throw new IllegalArgumentException("Showroom named "+ showroomRequest.getName() + " already exists.");
        }
    }

    @Override
    public void removeShowroom(String name) {
        Showroom showroom = showroomRepository.findByName(name);
        showroomRepository.delete(showroom);
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
