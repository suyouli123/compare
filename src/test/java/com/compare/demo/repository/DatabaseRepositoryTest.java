package com.compare.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DatabaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @Test
    void databaseConnectionShouldWork() {
        assertThat(entityManager).isNotNull();
        assertThat(em).isNotNull();
    }

    @Test
    void entityManagerShouldBeConfigured() {
        assertThat(entityManager.getEntityManager()).isNotNull();
    }
}