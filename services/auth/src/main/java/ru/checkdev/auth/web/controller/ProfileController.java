package ru.checkdev.auth.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.checkdev.auth.dto.ProfileDTO;
import ru.checkdev.auth.service.ProfileService;

import java.util.List;

/**
 * CheckDev пробное собеседование
 * ProfileController контроллер отправки и приема DTO модели ProfileDTO
 *
 * @author Dmitry Stepanov
 * @version 22.09.2023T23:49
 */
@Tag(name = "ProfileController", description = "Profile REST API")
@RestController
@RequestMapping("/profiles")
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Обрабатывает get запрос на получение профиля пользователя по запрошенному ID.
     *
     * @param id ID ProfileDTO
     * @return ResponseEntity
     */
    @Operation(
            summary = "Get Profile by user id",
            description = ""
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable int id) {
        var profileDTO = profileService.findProfileByID(id);
        return new ResponseEntity<>(
                profileDTO.orElse(new ProfileDTO()),
                profileDTO.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    /**
     * Отправляет все профили пользователей
     *
     * @return ResponseEntity
     */
    @Operation(
            summary = "Get all profiles",
            description = "get all profiles iin order by creation date"
    )
    @GetMapping("/")
    public ResponseEntity<List<ProfileDTO>> getAllProfilesOrderByCreateDesc() {
        var profiles = profileService.findProfilesOrderByCreatedDesc();
        return new ResponseEntity<>(
                profiles,
                profiles.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @Operation(
            summary = "Get all profiles by user ids",
            description = "get all profiles associated with user ids provided by the incoming parameter"
    )
    @PostMapping("/by-ids")
    public ResponseEntity<List<ProfileDTO>> getProfilesByUserIds(@RequestBody List<Integer> userIds) {
        var profiles = profileService.findProfilesByUserIds(userIds);
        return new ResponseEntity<>(
                profiles,
                profiles.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
