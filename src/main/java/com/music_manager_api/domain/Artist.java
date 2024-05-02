package com.music_manager_api.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "artist")
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "nationality")
  private String nationality;

  @Column(name = "number_of_followers")
  private Integer numberOfFollowers;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL,
      fetch = FetchType.EAGER, orphanRemoval = true)
  private List<Song> songs = new ArrayList<>();

  public void removeSong(Song song) {
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
    Artist artist = (Artist) o;
    return getId() != null && Objects.equals(getId(), artist.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}