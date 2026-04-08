package edu.eci.dosw.tdd.persistence.relational.repository;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.relational.mapper.UserPersistenceMapper;
import edu.eci.dosw.tdd.persistence.relational.repository.JpaUserRepository;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("relational")
public class UserRepositoryJpaImpl implements UserRepository {

    private final JpaUserRepository repository;

    public UserRepositoryJpaImpl(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return UserPersistenceMapper.toModel(
                repository.save(UserPersistenceMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id).map(UserPersistenceMapper::toModel);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username).map(UserPersistenceMapper::toModel);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(UserPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}