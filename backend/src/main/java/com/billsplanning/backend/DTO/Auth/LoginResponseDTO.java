package com.billsplanning.backend.DTO.Auth;

public record LoginResponseDTO(Long userId, String username, String email, String token) {}
