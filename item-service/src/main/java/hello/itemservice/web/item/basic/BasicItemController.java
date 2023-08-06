package hello.itemservice.web.item.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addFrom() {
        return "basic/addForm";
    }

    /**
     * POST- Html Form
     * content-type : application/x-www-form-urlencoded
     * 메시지 바디에 쿼리 파라미터 형식으로 전달 -> @RequestParam, @ModelAttribute 둘 다 사용 가능 -> 이전 강의 참고
     */
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터 값을 프로퍼티 접근법(setXXX)으로 입력
     * @ModelAttribute 는 모델에 @ModelAttribute 로 지정한 객체를 자동으로 넣어줌
     * @ModelAttribute("item") Item item -> Model 에 @ModelAttribute 로 지정한 객체(Item)를 자동으로 등록
     * model.addAttribute("item", item); -> 자동 추가, 생략 가능
     */
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute("item", item) -> 자동 추가, 생략 가능
     * @ModelAttribute 생략시 model 에 저장되는 name 은 클래스의 첫글자만 소문자로 변경해서 등록(Item -> item)
     */
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item items) {
        itemRepository.save(items);
        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute("item", item) 자동 추가
     */
    //@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    /**
     * Redirect
     * 상품 수정은 완료 후 뷰 템플릿을 호출 대신 상품 상세 화면으로 이동하도록 리다이렉트를 호출
     * 스프링은 redirect:/... 으로 편리하게 리다이렉트를 지원
     * redirect:/basic/items/{itemId}"
     * 컨트롤러에 매핑된 @PathVariable 의 값은 redirect 에도 사용 가능
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
