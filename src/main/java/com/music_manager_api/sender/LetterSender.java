package com.music_manager_api.sender;

import com.music_manager_api.web.dto.letter.LetterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LetterSender {

  @Value("${kafka.topic.letterReceived}")
  private String topicName;

  private final KafkaTemplate<String, LetterDto> kafkaTemplate;

  public void sendMessage(LetterDto letterDto) {
    kafkaTemplate.send(topicName, letterDto);
  }
}
