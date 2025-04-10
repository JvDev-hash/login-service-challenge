package com.usercard.login.model;

import jakarta.persistence.*;

@Entity
@Table(name="tbl_users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", columnDefinition = "TEXT", nullable = false)
    private String senha;

    // Coluna para salvar as permissões do usuário
    @Column(name = "permissoes", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserPermissions permissoes;

    public Usuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserPermissions getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(UserPermissions permissoes) {
        this.permissoes = permissoes;
    }
}
