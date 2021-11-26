package computer.shop.models.view;

import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerOfferDetailsView {
    private Long id;
    private String name;
    private BigDecimal price;
    private String sellerFullName;
    private String computerName;
    private String computerDescription;
    private LocalDate computerManufacturedOn;
    private String computerBox;

    private String computerVideoCard;
    private String computerProcessor;
    private String computerRam;
    private String computerPowerSupply;
    private String computerDrive;
    private String computerDriveTypeName;

    public ComputerOfferDetailsView() {
    }

    public String getName() {
        return name;
    }

    public ComputerOfferDetailsView setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ComputerOfferDetailsView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSellerFullName() {
        return sellerFullName;
    }

    public ComputerOfferDetailsView setSellerFullName(String sellerFullName) {
        this.sellerFullName = sellerFullName;
        return this;
    }

    public String getComputerName() {
        return computerName;
    }

    public ComputerOfferDetailsView setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    public String getComputerDescription() {
        return computerDescription;
    }

    public ComputerOfferDetailsView setComputerDescription(String computerDescription) {
        this.computerDescription = computerDescription;
        return this;
    }

    public LocalDate getComputerManufacturedOn() {
        return computerManufacturedOn;
    }

    public ComputerOfferDetailsView setComputerManufacturedOn(LocalDate computerManufacturedOn) {
        this.computerManufacturedOn = computerManufacturedOn;
        return this;
    }

    public String getComputerBox() {
        return computerBox;
    }

    public ComputerOfferDetailsView setComputerBox(String computerBox) {
        this.computerBox = computerBox;
        return this;
    }

    public String getComputerVideoCard() {
        return computerVideoCard;
    }

    public ComputerOfferDetailsView setComputerVideoCard(String computerVideoCard) {
        this.computerVideoCard = computerVideoCard;
        return this;
    }

    public String getComputerProcessor() {
        return computerProcessor;
    }

    public ComputerOfferDetailsView setComputerProcessor(String computerProcessor) {
        this.computerProcessor = computerProcessor;
        return this;
    }

    public String getComputerRam() {
        return computerRam;
    }

    public ComputerOfferDetailsView setComputerRam(String computerRam) {
        this.computerRam = computerRam;
        return this;
    }

    public String getComputerPowerSupply() {
        return computerPowerSupply;
    }

    public ComputerOfferDetailsView setComputerPowerSupply(String computerPowerSupply) {
        this.computerPowerSupply = computerPowerSupply;
        return this;
    }

    public String getComputerDrive() {
        return computerDrive;
    }

    public ComputerOfferDetailsView setComputerDrive(String computerDrive) {
        this.computerDrive = computerDrive;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ComputerOfferDetailsView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getComputerDriveTypeName() {
        return computerDriveTypeName;
    }

    public ComputerOfferDetailsView setComputerDriveTypeName(String computerDriveTypeName) {
        this.computerDriveTypeName = computerDriveTypeName;
        return this;
    }
}
