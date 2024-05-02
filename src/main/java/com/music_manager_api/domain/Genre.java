package com.music_manager_api.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.*;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
  private Set<Song> songs = new LinkedHashSet<>();


  public void removeSong(Song song){
    this.songs.remove(song);
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Genre genre = (Genre) o;
    return getId() != null && Objects.equals(getId(), genre.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}