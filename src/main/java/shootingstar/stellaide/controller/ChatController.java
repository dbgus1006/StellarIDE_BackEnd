package shootingstar.stellaide.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shootingstar.stellaide.entity.chat.ChatRoom;
import shootingstar.stellaide.repository.chatRoom.dto.FindAllChatMessageByRoomIdDTO;
import shootingstar.stellaide.service.ChatService;
import shootingstar.stellaide.service.dto.ChatRoomDTO;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    /**
     채팅방 목록 나열
     @RequestMapping("chat/chatList")
     public String chatList(Model model){
     List<ChatRoom> roomList = chatService.findAllRoom();
     model.addAttribute("roomList",roomList);
     return "chatList";
     }
     */

    /**
     * 채팅방 생성
     * containerId 받아오기
     */
    @PostMapping("/createRoom")
    public ResponseEntity<String> createRoom(@RequestParam(value = "roomId",required = false) Long roomId){
        chatService.createRoom(roomId);
        return ResponseEntity.ok().body("채팅방 생성");
    }
    @GetMapping("/chatRoom")
    public ResponseEntity<ChatRoomDTO> chatRoom(@Valid @RequestBody ChatRoom chatRoom){
        ChatRoomDTO chatRoomDTO = chatService.findRoomById(chatRoom.getChatRoomId());
        return ResponseEntity.ok().body(chatRoomDTO);
    }

    @GetMapping("/globalChatRoom")
    public ResponseEntity<ChatRoomDTO> globalChatRoom(@RequestParam(value = "chatRoomId", required = false) Long chatRoomId){
        ChatRoomDTO chatRoomDTO = chatService.findRoomById(chatRoomId);
        return ResponseEntity.ok().body(chatRoomDTO);
    }
    @GetMapping("/container/loadHistory")
    public ResponseEntity<?> getAllListPage(@RequestParam("roomId") Long roomId,
                                             @PageableDefault(size =100) Pageable pageable){

        Page<FindAllChatMessageByRoomIdDTO> findAllChatMessageByRoomIdDTOPage = chatService.getAllMessagePage(roomId, pageable);
        return ResponseEntity.ok().body(findAllChatMessageByRoomIdDTOPage);
    }
}
