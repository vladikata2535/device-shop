package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.VideoCardEntity;
import computer.shop.models.entity.enums.componentsEnums.VideoCardEnum;
import computer.shop.repository.VideoCardRepository;
import computer.shop.service.VideoCardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class VideoCardServiceImpl implements VideoCardService {

    private final VideoCardRepository videoCardRepository;

    public VideoCardServiceImpl(VideoCardRepository videoCardRepository) {
        this.videoCardRepository = videoCardRepository;
    }

    @Override
    public void initializeVideoCards() {
        Arrays.stream(VideoCardEnum.values())
                .forEach(videoCardEnum -> {
                    VideoCardEntity videoCard = null;

                    switch (videoCardEnum){
                        case AMD_Radeon_RX_5700:
                            videoCard = new VideoCardEntity(videoCardEnum, "8GB GDDR6", 5870, "The AMD Radeon RX 5700 proves that AMD can make graphics cards that aren't just incredibly powerful, but also offer excellent value for money. For the price, you're getting a mid-range card that can easily handle the latest games at 1080p and 1440p at their highest settings, which means getting amazing graphics in your games is now more accessible than ever before.", BigDecimal.valueOf(399.99));
                            break;
                        case AMD_Radeon_RX_6800:
                            videoCard = new VideoCardEntity(videoCardEnum, "16GB GDDR6", 12875, "AMD is gunning for Nvidia's crown when it comes to high-end performance in graphics cards, and the battle between the two companies means that both are now releasing powerful graphics cards at more competitive prices.", BigDecimal.valueOf(1799.99));
                            break;
                        case MSI_GeForce_RTX_3070:
                            videoCard = new VideoCardEntity(videoCardEnum, "8GB GDDR6", 5888, "This high-end MSI card with Nvidia's GeForce RTX 3070 is one of the best graphics cards readily available. It's expensive, but it's more affordable than many professional cards, and it can handle intensive creative workloads. For creatives looking for outstanding performance but at a slightly more accessible price, this option offers good value for money. Whether you're a gamer looking for the best experience at 4K resolutions or a creative who needs professional-grade performance without the huge price tag, this is definitely worth considering.", BigDecimal.valueOf(1849.99));
                            break;
                        case MSI_RTX_3090_Gaming_X_TRIO:
                            videoCard = new VideoCardEntity(videoCardEnum, "24GB GDDR6X", 18888, "For those who need the very best, the GeForce RTX 3090 Gaming X Trio, makes the existing RTX 3090 even more powerful. It raises the boost clock and slightly raises the power limit. Although it's still an RTX 3090, it has the cooling power to handle this GPU at its stock settings and enough drive to overclock it and make it even more powerful. For game creators, It's the icing on the cake for a GPU that makes small work of all PC games at 4K.", BigDecimal.valueOf(3699.99));
                            break;
                        case Nvidia_Quadro_RTX_4000:
                            videoCard = new VideoCardEntity(videoCardEnum, "8GB GDDR6", 2304, "The Nvidia Quadro RTX 4000 is highly recommended for a workstation-class graphics card at a (just about) affordable price. It boasts excellent performance in design applications and comes in a svelte single-slot design that helps it fit into small cases. It also requires less power than a bulkier GeForce card.", BigDecimal.valueOf(1149.99));
                            break;
                        case Nvidia_Quadro_RTX_5000:
                            videoCard = new VideoCardEntity(videoCardEnum, "16GB GDDR6", 3072, "If you’re not interested in gaming, the Quadros may be a better choice for creative software than Nvidia's GeForce cards. While the 8GB Quadro RTX 4000 is the more affordable choice, the 16GB Quadro RTX 5000 packs in a lot more performance, making it better for demanding users who are willing to shell out for serious performance.", BigDecimal.valueOf(2189.99));
                            break;
                        case Nvidia_GeForce_GTX_1660_Ti:
                            videoCard = new VideoCardEntity(videoCardEnum, "6GB GDDR6", 1536, "The Geforce GTX 1660 Ti is a much more affordable Nvidia graphics card than the pricey, high-end RTX series. It's based on the newer 12nm Turning architecture of the RTX cards, but without the ray tracing hardware.", BigDecimal.valueOf(959.99));
                            break;
                        case Nvidia_GeForce_RTX_2080_Ti:
                            videoCard = new VideoCardEntity(videoCardEnum, "11GB GDDR6", 4352, "For every generation, Nvidia releases a flagship model, and then a second, steroid-enhanced version of it with pricing that makes it an option for very serious users. The RTX 2080 Ti remains a brilliant GPU with 4,352 Cuda cores, almost double the ray tracing hardware and graphics processing power of the vanilla RTX 2080.", BigDecimal.valueOf(2429.99));
                            break;
                        case Gigabyte_Aorus_GeForce_RTX_3080:
                            videoCard = new VideoCardEntity(videoCardEnum, "10GB GDDR6", 8704, "The Gigabyte AORUS GeForce RTX 3080 Xtreme Graphics Card comes with Nvidia's GeForce RTX 3080, which is one of the best graphics cards you can buy right now. The RTX 3080 brings all the advancements of Nvidia's latest Ampere architecture, including next-generation ray-tracing capabilities and 10GB of fast GDDR6X memory, which means it can easily handle 4K gaming.", BigDecimal.valueOf(2629.99));
                            break;
                    }


                    videoCardRepository.save(videoCard);
                });
    }

    @Override
    public VideoCardEntity findVideoCardByName(VideoCardEnum videoCardEnum) {
        return videoCardRepository.findByName(videoCardEnum);
    }
}
