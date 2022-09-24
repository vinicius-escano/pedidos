package com.application.pedidoapi.impl;

import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.ProdutoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    @Override
    public Optional<Produto> findByUUID(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<Produto> findAll() {
        return null;
    }

    @Override
    public List<Produto> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Produto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Produto> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Produto entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Produto> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Produto> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Produto> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Produto> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Produto> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Produto getOne(Integer integer) {
        return null;
    }

    @Override
    public Produto getById(Integer integer) {
        return null;
    }

    @Override
    public Produto getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Produto> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Produto> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Produto> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Produto> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Produto> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Produto> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Produto, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
