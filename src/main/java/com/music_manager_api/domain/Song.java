package com.music_manager_api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "song")
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @ManyToOne
  @JoinColumn(name = "artist_id", nullable = false)
  private Artist artist;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "song_genres",
      joinColumns = @JoinColumn(name = "song_id"),
      inverseJoinColumns = @JoinColumn(name = "genres_id"))
  private Set<Genre> genres = new LinkedHashSet<>();

  @Column(name = "duration")
  private Integer duration;

  public void addGenre(Genre genre){
    this.genres.add(genre);
    genre.getSongs().add(this);
  }

  public void clearGenres(){
    this.genres.forEach(genre -> genre.removeSong(this));
    this.genres.clear();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Song song = (Song) o;
    return getId() != null && Objects.equals(getId(), song.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}