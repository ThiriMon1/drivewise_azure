package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.exception.make.MakeNotFoundException;
import edu.miu.cse.drivewise.mapper.MakeMapper;
import edu.miu.cse.drivewise.model.Make;
import edu.miu.cse.drivewise.repository.MakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MakeServiceImplTest {

    @Mock
    private MakeRepository makeRepository;

    @Mock
    private MakeMapper makeMapper;

    @InjectMocks
    private MakeServiceImpl makeService;

    private Make make;
    private MakeRequestDto makeRequestDto;
    private MakeResponseDto makeResponseDto;

    @BeforeEach
    void setUp() {
        // Initialize objects
        make = new Make();
        make.setMakeId(1L);
        make.setMakeName("Toyota");

        makeRequestDto = new MakeRequestDto("Toyota");

        makeResponseDto = new MakeResponseDto("Toyota");
    }

    @Test
    void saveMake_Success() {
        when(makeMapper.makeRequestDtoToMake(makeRequestDto)).thenReturn(make);
        when(makeRepository.save(make)).thenReturn(make);
        when(makeMapper.makeToMakeResponseDto(make)).thenReturn(makeResponseDto);

        Optional<MakeResponseDto> result = makeService.saveMake(makeRequestDto);

        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().makeName());
        verify(makeRepository, times(1)).save(make);
        verify(makeMapper, times(1)).makeRequestDtoToMake(makeRequestDto);
        verify(makeMapper, times(1)).makeToMakeResponseDto(make);
    }

    @Test
    void findMakeByName_Success() {
        when(makeRepository.findMakeByMakeName("Toyota")).thenReturn(Optional.of(make));
        when(makeMapper.makeToMakeResponseDto(make)).thenReturn(makeResponseDto);

        Optional<MakeResponseDto> result = makeService.findMakeByName("Toyota");

        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().makeName());
        verify(makeRepository, times(1)).findMakeByMakeName("Toyota");
        verify(makeMapper, times(1)).makeToMakeResponseDto(make);
    }

    @Test
    void findMakeByName_NotFound() {
        when(makeRepository.findMakeByMakeName("Honda")).thenReturn(Optional.empty());

        MakeNotFoundException exception = assertThrows(MakeNotFoundException.class, () -> {
            makeService.findMakeByName("Honda");
        });

        assertEquals("Honda is not found", exception.getMessage());
        verify(makeRepository, times(1)).findMakeByMakeName("Honda");
        verifyNoInteractions(makeMapper);
    }

    @Test
    void findAllMake_Success() {
        List<Make> makeList = new ArrayList<>();
        makeList.add(make);

        List<MakeResponseDto> responseList = new ArrayList<>();
        responseList.add(makeResponseDto);

        when(makeRepository.findAll()).thenReturn(makeList);
        when(makeMapper.makeToMakeResponseDto(make)).thenReturn(makeResponseDto);

        List<MakeResponseDto> result = makeService.findAllMake();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).makeName());
        verify(makeRepository, times(1)).findAll();
        verify(makeMapper, times(1)).makeToMakeResponseDto(make);
    }

    @Test
    void findAllMake_EmptyList() {
        when(makeRepository.findAll()).thenReturn(new ArrayList<>());

        List<MakeResponseDto> result = makeService.findAllMake();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(makeRepository, times(1)).findAll();
        verifyNoInteractions(makeMapper);
    }
}
