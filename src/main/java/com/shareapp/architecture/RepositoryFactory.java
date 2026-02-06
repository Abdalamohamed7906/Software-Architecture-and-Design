package com.shareapp.architecture;

import com.shareapp.repository.IStockRepository;
import com.shareapp.repository.MockStockRepository;

/**
 * Factory Pattern - Repository Factory
 * 
 * Demonstrates the Factory Pattern for creating repository instances.
 * Provides centralized control over which implementation is used.
 * 
 * Benefits:
 * - Encapsulates object creation logic
 * - Easy to switch between implementations
 * - Supports configuration-based selection
 * 
 * Sprint 1: Abstract implementation demonstrating Factory Pattern
 * Future Sprints: Will support SQLite and JSON repository selection
 */
public class RepositoryFactory {
    
    /**
     * Enumeration of supported repository types
     */
    public enum RepositoryType {
        MOCK,      // In-memory mock (Sprint 1)
        SQLITE,    // SQLite database (Future)
        JSON       // JSON file storage (Future)
    }
    
    /**
     * Creates a repository instance based on the specified type
     * 
     * This method demonstrates the Simple Factory Pattern.
     * Future implementation will read from configuration file.
     * 
     * @param type Type of repository to create
     * @return Instance of IStockRepository
     * @throws IllegalArgumentException if type is unsupported
     */
    public static IStockRepository createRepository(RepositoryType type) {
        System.out.println(String.format("[Factory] Creating repository of type: %s", type));
        
        return switch (type) {
            case MOCK -> new MockStockRepository();
            case SQLITE -> throw new UnsupportedOperationException(
                "SQLite repository not yet implemented (Sprint 2)");
            case JSON -> throw new UnsupportedOperationException(
                "JSON repository not yet implemented (Sprint 2)");
        };
    }
    
    /**
     * Creates a default repository (currently Mock for Sprint 1)
     * 
     * Future: Will read from configuration or environment variable
     * 
     * @return Default IStockRepository instance
     */
    public static IStockRepository createDefaultRepository() {
        // For Sprint 1, default is MOCK
        // Future: read from application.properties or environment
        return createRepository(RepositoryType.MOCK);
    }
    
    /**
     * Creates a repository based on configuration string
     * Useful for runtime configuration
     * 
     * @param configValue Configuration value ("mock", "sqlite", "json")
     * @return Configured IStockRepository instance
     */
    public static IStockRepository createRepositoryFromConfig(String configValue) {
        if (configValue == null || configValue.trim().isEmpty()) {
            return createDefaultRepository();
        }
        
        RepositoryType type = switch (configValue.toLowerCase().trim()) {
            case "mock" -> RepositoryType.MOCK;
            case "sqlite" -> RepositoryType.SQLITE;
            case "json" -> RepositoryType.JSON;
            default -> {
                System.err.println(String.format(
                    "[Factory] Unknown repository type '%s', using default", configValue));
                yield RepositoryType.MOCK;
            }
        };
        
        return createRepository(type);
    }
}
