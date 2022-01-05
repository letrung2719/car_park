package com.example.carpark.service.impl;

import com.example.carpark.dto.CarDto;
import com.example.carpark.model.Car;
import com.example.carpark.repository.CarRepository;
import com.example.carpark.service.ICarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService implements ICarService {
    private CarRepository carRepository;
    private ModelMapper modelMapper;

    public CarService(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    //get all cars
    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //add new car
    @Override
    public Car addNewCar(CarDto carDto) {
        return carRepository.save(this.mapToEntity(carDto));
    }

    //delete car by license plate
    @Override
    public String deleteCarByLicensePlate(String licensePlate) {
        if (this.existsById(licensePlate)){
            carRepository.deleteById(licensePlate);
            return "Delete car successfully!";
        }else{
            return "This id is not existed!";
        }
    }

    //check car existed
    @Override
    public boolean existsById(String s) {
        return carRepository.existsById(s);
    }

    //convert Entity to DTO
    @Override
    public CarDto mapToDto(Car car) {
        CarDto carDto = modelMapper.map(car, CarDto.class);
        return carDto;
    }

    //convert DTO to Entity
    @Override
    public Car mapToEntity(CarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        return car;
    }
}
