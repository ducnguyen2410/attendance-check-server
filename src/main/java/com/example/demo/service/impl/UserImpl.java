package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.*;
import com.example.demo.payload.OvertimePayload;
import com.example.demo.payload.AttendancePayload;
import com.example.demo.payload.UserPayload;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.example.demo.util.DateUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserService {
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final PositionLevelRepository positionLevelRepository;
    private final ContractRepository contractRepository;
    private final UserDataRepository userDataRepository;
    private final AttendanceRepository attendanceRepository;
    private final OvertimeRepository overtimeRepository;
    private final OvertimeSalaryRepository overtimeSalaryRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserImpl(UserRepository userRepository, PositionRepository positionRepository, PositionLevelRepository positionLevelRepository, ContractRepository contractRepository, UserDataRepository userDataRepository, AttendanceRepository attendanceRepository, OvertimeRepository overtimeRepository, OvertimeSalaryRepository overtimeSalaryRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.positionLevelRepository = positionLevelRepository;
        this.contractRepository = contractRepository;
        this.userDataRepository = userDataRepository;
        this.attendanceRepository = attendanceRepository;
        this.overtimeRepository = overtimeRepository;
        this.overtimeSalaryRepository = overtimeSalaryRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto createUser(UserPayload userPayload) {
        User existUser = userRepository.getUserByUsername(userPayload.getUsername());
        if (existUser != null) {
            throw new AlreadyExistsException("User already exists.");
        }

        userPayload.setPassword(passwordEncoder.encode(userPayload.getPassword()));
        User user = UserMapper.toUser(userPayload);

        Set<Role> assignedRoles;

        if (userPayload.getRoles() != null && !userPayload.getRoles().isEmpty()) {
            assignedRoles = userPayload.getRoles().stream()
                    .map(roleName -> {
                        Role role = roleRepository.findByName(roleName.toUpperCase());
                        if (role == null) {
                            throw new DoesNotExistException("Role '" + roleName + "' not found.");
                        }
                        return role;
                    })
                    .collect(Collectors.toSet());
        } else {
            // Default to "USER" role if nothing is provided
            Role defaultRole = roleRepository.findByName("USER");
            if (defaultRole == null) throw new DoesNotExistException("Default role not found.");
            assignedRoles = Set.of(defaultRole);
        }

        user.setRoles(assignedRoles);
        userRepository.save(user);

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UserPayload userPayload) {
        User user = userRepository.findById(userPayload.getId())
                .orElseThrow(() -> new DoesNotExistException("User does not exist."));
        if(userPayload.getPositionId() != null) {
            Position position = positionRepository.findById(userPayload.getPositionId())
                    .orElseThrow(() -> new DoesNotExistException("Position does not exist."));
            user.setPosition(position);
        }
        if(userPayload.getPositionLevelId() != null) {
            PositionLevel positionLevel = positionLevelRepository.findById(userPayload.getPositionLevelId())
                    .orElseThrow(() -> new DoesNotExistException("Position level does not exist."));
            user.setPositionLevel(positionLevel);
        }
        if(userPayload.getContractId() != null) {
            Contract contract = contractRepository.findById(userPayload.getContractId())
                    .orElseThrow(() -> new DoesNotExistException("Contract does not exist."));
            user.setContract(contract);
        }
        if(userPayload.getUserDataId() != null) {
            UserData userData = userDataRepository.findById(userPayload.getUserDataId())
                    .orElseThrow(() -> new DoesNotExistException("User Data does not exist."));
            user.setUserData(userData);
        }
        if(userPayload.getAttendancePayloads() != null) {
            List<Attendance>  attendances = new ArrayList<>();
            for (AttendancePayload attendancePayload : userPayload.getAttendancePayloads()) {
               Attendance attendance;
               if(attendancePayload.getId() != null) {
                   attendance = attendanceRepository.findById(attendancePayload.getId())
                           .orElseThrow(() -> new DoesNotExistException("Tardy does not exist."));
               } else {
                   attendance = new Attendance();
                   attendance.setUser(user);
               }

               attendance.setCheckIn(DateUtil.toLocalTime(attendancePayload.getCheckIn()));
               attendance.setCheckOut(DateUtil.toLocalTime(attendancePayload.getCheckOut()));
               attendances.add(attendance);
            }
            user.getAttendances().clear();
            user.getAttendances().addAll(attendances);
            user.setAttendances(attendances);
        }
        if (userPayload.getOvertimePayloads() != null) {
            List<Overtime> newOvertimes = new ArrayList<>();
            for (OvertimePayload oPayload : userPayload.getOvertimePayloads()) {
                Overtime overtime;
                if (oPayload.getId() != null) {
                    overtime = overtimeRepository.findById(oPayload.getId())
                            .orElseThrow(() -> new DoesNotExistException("Overtime not found"));
                } else {
                    overtime = new Overtime();
                    overtime.setUser(user);
                }

                overtime.setDuration(oPayload.getDuration());
                OvertimeSalary salary = overtimeSalaryRepository.findById(oPayload.getOvertimeSalaryId())
                        .orElseThrow(() -> new DoesNotExistException("OvertimeSalary not found"));
                overtime.setOvertimeSalary(salary);

                newOvertimes.add(overtime);
            }
            user.getOvertimes().clear();
            user.getOvertimes().addAll(newOvertimes);
            user.setOvertimes(newOvertimes);
        }
        UserMapper.mapToUser(userPayload, user);
        if (userPayload.getRoles() != null && !userPayload.getRoles().isEmpty()) {
            Set<Role> updatedRoles = userPayload.getRoles().stream()
                    .map(roleRepository::findByName)
                    .filter(Objects::nonNull) // skip nulls (not found roles)
                    .collect(Collectors.toSet());
            user.setRoles(updatedRoles);
        }
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto deleteUser(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                throw new DoesNotExistException("User does not exist.");
            }
            userRepository.deleteById(id);
            return UserMapper.toUserDto(user);
        } catch (RuntimeException e) {
            throw new DoesNotExistException("User does not exist.");
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.toUserDto(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }

    @Override
    public UserDto getUserByUserName(String username) {
        return UserMapper.toUserDto(userRepository.findByUsername(username));
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user: users) {
            userDtoList.add(UserMapper.toUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDataDto getUserDataByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new DoesNotExistException("User does not exist.");
        }
        return UserDataMapper.toUserDataDto(user.getUserData());
    }

    @Override
    public UserDto updateUserStatus(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new DoesNotExistException("User does not exist.");
        }
        user.setStatus(!user.getStatus());
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDetailDto getUserDetail(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new DoesNotExistException("User does not exist.");
        }
        return UserMapper.toUserDetailDto(user);
    }

    @Override
    public UserCheckListDto getUserCheckListDto(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new DoesNotExistException("User does not exist.");
        }
        List<Attendance> attendances = user.getAttendances();
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for(Attendance attendance: attendances) {
            attendanceDtoList.add(AttendanceMapper.toAttendanceDto(attendance));
        }
        List<Overtime> overtimes = user.getOvertimes();
        List<OvertimeDto> overtimeDtoList = new ArrayList<>();
        for(Overtime overtime: overtimes) {
            overtimeDtoList.add(OvertimeMapper.toOvertimeDto(overtime));
        }
        return UserMapper.toUserCheckListDto(user, attendanceDtoList, overtimeDtoList);
    }
}
