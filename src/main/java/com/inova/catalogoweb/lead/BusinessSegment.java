package com.inova.catalogoweb.lead;

public enum BusinessSegment {

    LOJA_DE_ROUPAS("Loja de Roupas"),
    CALCADOS("Calçados"),
    ACESSORIOS("Acessórios"),
    JOALHERIA("Joalheria"),
    COSMETICOS("Cosméticos"),
    PERFUMARIA("Perfumaria"),
    FARMACIA("Farmácia"),
    MERCADO("Mercado"),
    MERCEARIA("Mercearia"),
    DISTRIBUIDORA("Distribuidora"),
    RESTAURANTE("Restaurante"),
    PIZZARIA("Pizzaria"),
    HAMBURGUERIA("Hamburgueria"),
    LANCHONETE("Lanchonete"),
    PADARIA("Padaria"),
    CAFETERIA("Cafeteria"),
    DOCERIA("Doceria"),
    PET_SHOP("Pet Shop"),
    CLINICA("Clínica"),
    SALAO_DE_BELEZA("Salão de Beleza"),
    BARBEARIA("Barbearia"),
    AUTOPECAS("Autopeças"),
    OFICINA("Oficina Mecânica"),
    MATERIAIS_DE_CONSTRUCAO("Materiais de Construção"),
    MOVEIS("Móveis"),
    DECORACAO("Decoração"),
    PAPELARIA("Papelaria"),
    LIVRARIA("Livraria"),
    ELETRONICOS("Eletrônicos"),
    INFORMATICA("Informática"),
    ASSISTENCIA_TECNICA("Assistência Técnica"),
    FLORICULTURA("Floricultura"),
    PRESENTES("Presentes"),
    ARTESANATO("Artesanato"),
    AGROPECUARIA("Agropecuária"),
    OUTRO("Outro");

    private final String description;

    BusinessSegment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
