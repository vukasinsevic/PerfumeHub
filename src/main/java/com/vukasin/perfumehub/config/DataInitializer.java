package com.vukasin.perfumehub.config;

import com.vukasin.perfumehub.entity.*;
import com.vukasin.perfumehub.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            BrandRepository brandRepository,
            GenderRepository genderRepository,
            ConcentrationRepository concentrationRepository,
            NoteRepository noteRepository,
            AccordRepository accordRepository,
            SeasonRepository seasonRepository,
            PerfumeRepository perfumeRepository,
            ProductVariantRepository productVariantRepository
    ) {
        return args -> {

            if (perfumeRepository.count() > 0) {
                return;
            }

            // Reference data
            Brand dior = brandRepository.save(new Brand("Dior"));
            Brand armani = brandRepository.save(new Brand("Armani"));
            Brand jpg = brandRepository.save(new Brand("Jean Paul Gaultier"));
            Brand chanel = brandRepository.save(new Brand("Chanel"));
            Brand ysl = brandRepository.save(new Brand("Yves Saint Laurent"));
            Brand creed = brandRepository.save(new Brand("Creed"));
            Brand armaf = brandRepository.save(new Brand("Armaf"));
            Brand versace = brandRepository.save(new Brand("Versace"));
            Brand louisVuitton = brandRepository.save(new Brand("Louis Vuitton"));
            Brand afnan = brandRepository.save(new Brand("Afnan"));

            Gender male = genderRepository.save(new Gender("MALE"));
            Gender female = genderRepository.save(new Gender("FEMALE"));
            Gender unisex = genderRepository.save(new Gender("UNISEX"));

            Concentration edt =
                    concentrationRepository.save(new Concentration("EAU_DE_TOILETTE"));

            Concentration edp =
                    concentrationRepository.save(new Concentration("EAU_DE_PARFUM"));

            Concentration parfum =
                    concentrationRepository.save(new Concentration("PARFUM"));

            Concentration extrait =
                    concentrationRepository.save(new Concentration("EXTRAIT_DE_PARFUM"));

            Note bergamot = noteRepository.save(new Note("Bergamot"));
            Note vanilla = noteRepository.save(new Note("Vanilla"));
            Note lavender = noteRepository.save(new Note("Lavender"));
            Note pepper = noteRepository.save(new Note("Pepper"));
            Note amber = noteRepository.save(new Note("Amber"));
            Note iris = noteRepository.save(new Note("Iris"));
            Note tobacco = noteRepository.save(new Note("Tobacco"));
            Note oud = noteRepository.save(new Note("Oud"));
            Note cardamom = noteRepository.save(new Note("Cardamom"));
            Note lemon = noteRepository.save(new Note("Lemon"));
            Note grapefruit = noteRepository.save(new Note("Grapefruit"));
            Note cedar = noteRepository.save(new Note("Cedar"));
            Note sandalwood = noteRepository.save(new Note("Sandalwood"));
            Note tonkaBean = noteRepository.save(new Note("Tonka Bean"));
            Note apple = noteRepository.save(new Note("Apple"));
            Note pineapple = noteRepository.save(new Note("Pineapple"));
            Note rose = noteRepository.save(new Note("Rose"));
            Note jasmine = noteRepository.save(new Note("Jasmine"));
            Note leather = noteRepository.save(new Note("Leather"));
            Note cinnamon = noteRepository.save(new Note("Cinnamon"));
            Note musk = noteRepository.save(new Note("Musk"));
            Note ginger = noteRepository.save(new Note("Ginger"));
            Note orange = noteRepository.save(new Note("Orange"));
            Note mandarin = noteRepository.save(new Note("Mandarin"));
            Note mint = noteRepository.save(new Note("Mint"));
            Note violet = noteRepository.save(new Note("Violet"));
            Note patchouli = noteRepository.save(new Note("Patchouli"));
            Note coconut = noteRepository.save(new Note("Coconut"));
            Note rum = noteRepository.save(new Note("Rum"));
            Note incense = noteRepository.save(new Note("Incense"));
            Note lime = noteRepository.save(new Note("Lime"));

            Accord fresh = accordRepository.save(new Accord("Fresh"));
            Accord woody = accordRepository.save(new Accord("Woody"));
            Accord sweet = accordRepository.save(new Accord("Sweet"));
            Accord warm = accordRepository.save(new Accord("Warm"));
            Accord spicy = accordRepository.save(new Accord("Spicy"));
            Accord aromatic = accordRepository.save(new Accord("Aromatic"));
            Accord citrus = accordRepository.save(new Accord("Citrus"));
            Accord powdery = accordRepository.save(new Accord("Powdery"));
            Accord tobaccoAccord = accordRepository.save(new Accord("Tobacco"));
            Accord leatherAccord = accordRepository.save(new Accord("Leather"));
            Accord aquatic = accordRepository.save(new Accord("Aquatic"));
            Accord fruity = accordRepository.save(new Accord("Fruity"));
            Accord green = accordRepository.save(new Accord("Green"));
            Accord smoky = accordRepository.save(new Accord("Smoky"));
            Accord tropical = accordRepository.save(new Accord("Tropical"));

            Season spring = seasonRepository.save(new Season("SPRING"));
            Season summer = seasonRepository.save(new Season("SUMMER"));
            Season fall = seasonRepository.save(new Season("FALL"));
            Season winter = seasonRepository.save(new Season("WINTER"));

            // 1. Dior Sauvage
            Perfume sauvage = new Perfume(
                    "Sauvage",
                    "Fresh, spicy and aromatic men's fragrance.",
                    2015,
                    "https://example.com/images/sauvage.jpg",
                    dior,
                    male,
                    edt
            );

            sauvage.addNote(bergamot);
            sauvage.addNote(pepper);
            sauvage.addNote(cedar);

            sauvage.addAccord(fresh);
            sauvage.addAccord(spicy);
            sauvage.addAccord(aromatic);

            sauvage.addSeason(spring);
            sauvage.addSeason(summer);
            sauvage.addSeason(fall);

            perfumeRepository.save(sauvage);

            productVariantRepository.save(
                    new ProductVariant(60, new BigDecimal("89.99"), 20, true, sauvage)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("119.99"), 12, true, sauvage)
            );


            // 2. Dior Homme Intense
            Perfume diorHommeIntense = new Perfume(
                    "Dior Homme Intense",
                    "Elegant, powdery and warm fragrance centered around iris.",
                    2011,
                    "https://example.com/images/dior-homme-intense.jpg",
                    dior,
                    male,
                    edp
            );

            diorHommeIntense.addNote(iris);
            diorHommeIntense.addNote(amber);
            diorHommeIntense.addNote(cedar);

            diorHommeIntense.addAccord(powdery);
            diorHommeIntense.addAccord(woody);
            diorHommeIntense.addAccord(warm);

            diorHommeIntense.addSeason(fall);
            diorHommeIntense.addSeason(winter);

            perfumeRepository.save(diorHommeIntense);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("109.99"), 10, true, diorHommeIntense)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("149.99"), 8, true, diorHommeIntense)
            );


            // 3. Armani Stronger With You Intensely
            Perfume strongerWithYou = new Perfume(
                    "Stronger With You Intensely",
                    "Warm, sweet and spicy fragrance.",
                    2019,
                    "https://example.com/images/swy-intensely.jpg",
                    armani,
                    male,
                    edp
            );

            strongerWithYou.addNote(vanilla);
            strongerWithYou.addNote(amber);
            strongerWithYou.addNote(cinnamon);
            strongerWithYou.addNote(tonkaBean);

            strongerWithYou.addAccord(sweet);
            strongerWithYou.addAccord(warm);
            strongerWithYou.addAccord(spicy);

            strongerWithYou.addSeason(fall);
            strongerWithYou.addSeason(winter);

            perfumeRepository.save(strongerWithYou);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("99.99"), 15, true, strongerWithYou)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("129.99"), 7, true, strongerWithYou)
            );


            // 4. Armani Acqua di Gio Parfum
            Perfume acquaDiGioParfum = new Perfume(
                    "Acqua di Gio Parfum",
                    "Fresh aquatic fragrance with aromatic and woody character.",
                    2023,
                    "https://example.com/images/acqua-di-gio-parfum.jpg",
                    armani,
                    male,
                    parfum
            );

            acquaDiGioParfum.addNote(bergamot);
            acquaDiGioParfum.addNote(incense);
            acquaDiGioParfum.addNote(cedar);

            acquaDiGioParfum.addAccord(fresh);
            acquaDiGioParfum.addAccord(aquatic);
            acquaDiGioParfum.addAccord(woody);

            acquaDiGioParfum.addSeason(spring);
            acquaDiGioParfum.addSeason(summer);

            perfumeRepository.save(acquaDiGioParfum);

            productVariantRepository.save(
                    new ProductVariant(75, new BigDecimal("109.99"), 12, true, acquaDiGioParfum)
            );
            productVariantRepository.save(
                    new ProductVariant(125, new BigDecimal("139.99"), 7, true, acquaDiGioParfum)
            );


            // 5. Jean Paul Gaultier Le Male Le Parfum
            Perfume leMaleLeParfum = new Perfume(
                    "Le Male Le Parfum",
                    "Warm aromatic fragrance with vanilla and lavender.",
                    2020,
                    "https://example.com/images/le-male-le-parfum.jpg",
                    jpg,
                    male,
                    parfum
            );

            leMaleLeParfum.addNote(vanilla);
            leMaleLeParfum.addNote(lavender);
            leMaleLeParfum.addNote(cardamom);

            leMaleLeParfum.addAccord(sweet);
            leMaleLeParfum.addAccord(warm);
            leMaleLeParfum.addAccord(aromatic);

            leMaleLeParfum.addSeason(fall);
            leMaleLeParfum.addSeason(winter);

            perfumeRepository.save(leMaleLeParfum);

            productVariantRepository.save(
                    new ProductVariant(75, new BigDecimal("89.99"), 15, true, leMaleLeParfum)
            );
            productVariantRepository.save(
                    new ProductVariant(125, new BigDecimal("109.99"), 12, true, leMaleLeParfum)
            );


            // 6. Jean Paul Gaultier Le Male Elixir
            Perfume leMaleElixir = new Perfume(
                    "Le Male Elixir",
                    "Rich sweet fragrance with vanilla, tobacco and warm spicy notes.",
                    2023,
                    "https://example.com/images/le-male-elixir.jpg",
                    jpg,
                    male,
                    parfum
            );

            leMaleElixir.addNote(vanilla);
            leMaleElixir.addNote(tobacco);
            leMaleElixir.addNote(lavender);
            leMaleElixir.addNote(tonkaBean);

            leMaleElixir.addAccord(sweet);
            leMaleElixir.addAccord(warm);
            leMaleElixir.addAccord(tobaccoAccord);

            leMaleElixir.addSeason(fall);
            leMaleElixir.addSeason(winter);

            perfumeRepository.save(leMaleElixir);

            productVariantRepository.save(
                    new ProductVariant(75, new BigDecimal("99.99"), 10, true, leMaleElixir)
            );
            productVariantRepository.save(
                    new ProductVariant(125, new BigDecimal("129.99"), 9, true, leMaleElixir)
            );


            // 7. Chanel Bleu de Chanel
            Perfume bleuDeChanel = new Perfume(
                    "Bleu de Chanel",
                    "Fresh woody aromatic fragrance with citrus and incense.",
                    2010,
                    "https://example.com/images/bleu-de-chanel.jpg",
                    chanel,
                    male,
                    edt
            );

            bleuDeChanel.addNote(grapefruit);
            bleuDeChanel.addNote(lemon);
            bleuDeChanel.addNote(cedar);
            bleuDeChanel.addNote(incense);

            bleuDeChanel.addAccord(citrus);
            bleuDeChanel.addAccord(woody);
            bleuDeChanel.addAccord(aromatic);

            bleuDeChanel.addSeason(spring);
            bleuDeChanel.addSeason(summer);
            bleuDeChanel.addSeason(fall);

            perfumeRepository.save(bleuDeChanel);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("99.99"), 14, true, bleuDeChanel)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("139.99"), 10, true, bleuDeChanel)
            );


            // 8. YSL Y Eau de Parfum
            Perfume yEdp = new Perfume(
                    "Y Eau de Parfum",
                    "Fresh aromatic fragrance combining fruit, spice and woods.",
                    2018,
                    "https://example.com/images/y-edp.jpg",
                    ysl,
                    male,
                    edp
            );

            yEdp.addNote(apple);
            yEdp.addNote(ginger);
            yEdp.addNote(bergamot);
            yEdp.addNote(cedar);

            yEdp.addAccord(fresh);
            yEdp.addAccord(aromatic);
            yEdp.addAccord(fruity);

            yEdp.addSeason(spring);
            yEdp.addSeason(summer);
            yEdp.addSeason(fall);

            perfumeRepository.save(yEdp);

            productVariantRepository.save(
                    new ProductVariant(60, new BigDecimal("89.99"), 15, true, yEdp)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("119.99"), 10, true, yEdp)
            );


            // 9. YSL MYSLF Le Parfum
            Perfume myslfLeParfum = new Perfume(
                    "MYSLF Le Parfum",
                    "Warm woody fragrance with floral and spicy facets.",
                    2024,
                    "https://example.com/images/myslf-le-parfum.jpg",
                    ysl,
                    male,
                    parfum
            );

            myslfLeParfum.addNote(orange);
            myslfLeParfum.addNote(pepper);
            myslfLeParfum.addNote(vanilla);
            myslfLeParfum.addNote(cedar);

            myslfLeParfum.addAccord(woody);
            myslfLeParfum.addAccord(warm);
            myslfLeParfum.addAccord(spicy);

            myslfLeParfum.addSeason(fall);
            myslfLeParfum.addSeason(winter);

            perfumeRepository.save(myslfLeParfum);

            productVariantRepository.save(
                    new ProductVariant(60, new BigDecimal("109.99"), 8, true, myslfLeParfum)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("149.99"), 6, true, myslfLeParfum)
            );


            // 10. Creed Aventus
            Perfume aventus = new Perfume(
                    "Aventus",
                    "Fruity and woody fragrance with smoky undertones.",
                    2010,
                    "https://example.com/images/aventus.jpg",
                    creed,
                    male,
                    edp
            );

            aventus.addNote(pineapple);
            aventus.addNote(bergamot);
            aventus.addNote(apple);
            aventus.addNote(musk);

            aventus.addAccord(fruity);
            aventus.addAccord(woody);
            aventus.addAccord(smoky);

            aventus.addSeason(spring);
            aventus.addSeason(summer);
            aventus.addSeason(fall);

            perfumeRepository.save(aventus);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("219.99"), 5, true, aventus)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("319.99"), 4, true, aventus)
            );


            // 11. Creed Green Irish Tweed
            Perfume greenIrishTweed = new Perfume(
                    "Green Irish Tweed",
                    "Fresh green and woody fragrance.",
                    1985,
                    "https://example.com/images/green-irish-tweed.jpg",
                    creed,
                    male,
                    edp
            );

            greenIrishTweed.addNote(lemon);
            greenIrishTweed.addNote(violet);
            greenIrishTweed.addNote(sandalwood);

            greenIrishTweed.addAccord(green);
            greenIrishTweed.addAccord(fresh);
            greenIrishTweed.addAccord(woody);

            greenIrishTweed.addSeason(spring);
            greenIrishTweed.addSeason(summer);

            perfumeRepository.save(greenIrishTweed);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("199.99"), 5, true, greenIrishTweed)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("289.99"), 3, true, greenIrishTweed)
            );


            // 12. Creed Virgin Island Water
            Perfume virginIslandWater = new Perfume(
                    "Virgin Island Water",
                    "Fresh tropical fragrance inspired by warm islands.",
                    2007,
                    "https://example.com/images/virgin-island-water.jpg",
                    creed,
                    unisex,
                    edp
            );

            virginIslandWater.addNote(coconut);
            virginIslandWater.addNote(bergamot);
            virginIslandWater.addNote(lime);
            virginIslandWater.addNote(rum);

            virginIslandWater.addAccord(tropical);
            virginIslandWater.addAccord(citrus);
            virginIslandWater.addAccord(fresh);

            virginIslandWater.addSeason(spring);
            virginIslandWater.addSeason(summer);

            perfumeRepository.save(virginIslandWater);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("189.99"), 6, true, virginIslandWater)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("269.99"), 4, true, virginIslandWater)
            );


            // 13. Armaf Club de Nuit Intense Man Extrait de Parfum
            Perfume clubDeNuit = new Perfume(
                    "Club de Nuit Intense Man Extrait de Parfum",
                    "Fruity, smoky and woody fragrance.",
                    2025,
                    "https://example.com/images/cdni-extrait.jpg",
                    armaf,
                    male,
                    extrait
            );

            clubDeNuit.addNote(lemon);
            clubDeNuit.addNote(pineapple);
            clubDeNuit.addNote(bergamot);
            clubDeNuit.addNote(musk);

            clubDeNuit.addAccord(fruity);
            clubDeNuit.addAccord(smoky);
            clubDeNuit.addAccord(woody);

            clubDeNuit.addSeason(spring);
            clubDeNuit.addSeason(fall);

            perfumeRepository.save(clubDeNuit);

            productVariantRepository.save(
                    new ProductVariant(105, new BigDecimal("69.99"), 18, true, clubDeNuit)
            );


            // 14. Armaf Urban Man Elixir
            Perfume urbanManElixir = new Perfume(
                    "Urban Man Elixir",
                    "Sweet fresh aromatic fragrance with spicy and woody facets.",
                    2022,
                    "https://example.com/images/urban-man-elixir.jpg",
                    armaf,
                    male,
                    edp
            );

            urbanManElixir.addNote(bergamot);
            urbanManElixir.addNote(lavender);
            urbanManElixir.addNote(amber);
            urbanManElixir.addNote(cedar);

            urbanManElixir.addAccord(fresh);
            urbanManElixir.addAccord(aromatic);
            urbanManElixir.addAccord(spicy);

            urbanManElixir.addSeason(spring);
            urbanManElixir.addSeason(fall);
            urbanManElixir.addSeason(winter);

            perfumeRepository.save(urbanManElixir);

            productVariantRepository.save(
                    new ProductVariant(105, new BigDecimal("49.99"), 20, true, urbanManElixir)
            );


            // 15. Versace Eros
            Perfume eros = new Perfume(
                    "Eros",
                    "Sweet aromatic fragrance with fresh mint and vanilla.",
                    2012,
                    "https://example.com/images/eros.jpg",
                    versace,
                    male,
                    edt
            );

            eros.addNote(mint);
            eros.addNote(apple);
            eros.addNote(vanilla);
            eros.addNote(tonkaBean);

            eros.addAccord(sweet);
            eros.addAccord(aromatic);
            eros.addAccord(fresh);

            eros.addSeason(fall);
            eros.addSeason(winter);

            perfumeRepository.save(eros);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("59.99"), 20, true, eros)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("79.99"), 14, true, eros)
            );


            // 16. Versace Dylan Blue
            Perfume dylanBlue = new Perfume(
                    "Dylan Blue",
                    "Fresh aromatic fragrance with citrus and woody character.",
                    2016,
                    "https://example.com/images/dylan-blue.jpg",
                    versace,
                    male,
                    edt
            );

            dylanBlue.addNote(bergamot);
            dylanBlue.addNote(grapefruit);
            dylanBlue.addNote(pepper);
            dylanBlue.addNote(musk);

            dylanBlue.addAccord(fresh);
            dylanBlue.addAccord(citrus);
            dylanBlue.addAccord(aromatic);

            dylanBlue.addSeason(spring);
            dylanBlue.addSeason(summer);

            perfumeRepository.save(dylanBlue);

            productVariantRepository.save(
                    new ProductVariant(50, new BigDecimal("54.99"), 18, true, dylanBlue)
            );
            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("74.99"), 13, true, dylanBlue)
            );


            // 17. Louis Vuitton Imagination
            Perfume imagination = new Perfume(
                    "Imagination",
                    "Fresh citrus aromatic fragrance with tea-like and spicy character.",
                    2021,
                    "https://example.com/images/imagination.jpg",
                    louisVuitton,
                    male,
                    edp
            );

            imagination.addNote(bergamot);
            imagination.addNote(ginger);
            imagination.addNote(cedar);

            imagination.addAccord(citrus);
            imagination.addAccord(fresh);
            imagination.addAccord(aromatic);

            imagination.addSeason(spring);
            imagination.addSeason(summer);

            perfumeRepository.save(imagination);

            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("289.99"), 5, true, imagination)
            );


            // 18. Louis Vuitton L'Immensité
            Perfume limmensite = new Perfume(
                    "L'Immensité",
                    "Fresh spicy citrus fragrance with aromatic woods.",
                    2018,
                    "https://example.com/images/limmensite.jpg",
                    louisVuitton,
                    male,
                    edp
            );

            limmensite.addNote(grapefruit);
            limmensite.addNote(ginger);
            limmensite.addNote(bergamot);
            limmensite.addNote(amber);

            limmensite.addAccord(citrus);
            limmensite.addAccord(spicy);
            limmensite.addAccord(fresh);

            limmensite.addSeason(spring);
            limmensite.addSeason(summer);

            perfumeRepository.save(limmensite);

            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("289.99"), 5, true, limmensite)
            );


            // 19. Afnan Supremacy Collector's Edition
            Perfume supremacyCollectorsEdition = new Perfume(
                    "Supremacy Collector's Edition",
                    "Fruity woody fragrance with warm and smoky nuances.",
                    2024,
                    "https://example.com/images/supremacy-collectors-edition.jpg",
                    afnan,
                    male,
                    extrait
            );

            supremacyCollectorsEdition.addNote(pineapple);
            supremacyCollectorsEdition.addNote(bergamot);
            supremacyCollectorsEdition.addNote(apple);
            supremacyCollectorsEdition.addNote(musk);

            supremacyCollectorsEdition.addAccord(fruity);
            supremacyCollectorsEdition.addAccord(woody);
            supremacyCollectorsEdition.addAccord(smoky);

            supremacyCollectorsEdition.addSeason(spring);
            supremacyCollectorsEdition.addSeason(fall);

            perfumeRepository.save(supremacyCollectorsEdition);

            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("64.99"), 16, true, supremacyCollectorsEdition)
            );


            // 20. Afnan Lynked Freedom
            Perfume lynkedFreedom = new Perfume(
                    "Lynked Freedom",
                    "Fresh modern fragrance with aromatic, woody and citrus facets.",
                    2025,
                    "https://example.com/images/lynked-freedom.jpg",
                    afnan,
                    male,
                    edp
            );

            lynkedFreedom.addNote(bergamot);
            lynkedFreedom.addNote(ginger);
            lynkedFreedom.addNote(cedar);
            lynkedFreedom.addNote(musk);

            lynkedFreedom.addAccord(fresh);
            lynkedFreedom.addAccord(aromatic);
            lynkedFreedom.addAccord(woody);

            lynkedFreedom.addSeason(spring);
            lynkedFreedom.addSeason(summer);

            perfumeRepository.save(lynkedFreedom);

            productVariantRepository.save(
                    new ProductVariant(100, new BigDecimal("59.99"), 15, true, lynkedFreedom)
            );

        };
    }
}