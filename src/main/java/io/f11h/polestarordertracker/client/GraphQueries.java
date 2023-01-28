package io.f11h.polestarordertracker.client;

public class GraphQueries {

    public static final String GET_ORDER_MODEL_OPERATION = "GetOrderModel";
    public static final String GET_ORDER_MODEL_QUERY = """
            query GetOrderModel($request: QueryRequest!) {
              order: getOrderModel(getOrderModelRequest: $request) {
                id
                orderNumber
                orderExternalReferenceNumber
                polestarId
                countryCode
                languageCode
                currency
                orderType
                polestarModel
                salesChannel
                consumerType
                financingOption
                loanContractAppendixFilePath
                version
                rebuilding
                updated
                depositInformation {
                  ...DepositInformationFields
                  __typename
                }
                partnerLocations {
                  locationId
                  capabilities
                  __typename
                }
                driver {
                  ...DriverFields
                  __typename
                }
                consumer {
                  ...ConsumerFields
                  __typename
                }
                state {
                  ...StateFields
                  __typename
                }
                financeInformation {
                  financeApplication {
                    ...FinanceApplicationFields
                    fileReferences {
                      ...FileRefenceFields
                      __typename
                    }
                    identifiers {
                      ...IdentifierFields
                      __typename
                    }
                    additionalInformation {
                      ...AdditionalInformationFields
                      __typename
                    }
                    __typename
                  }
                  __typename
                }
                personalFinanceInformation {
                  financeApplication {
                    ...PersonalFinanceApplicationFields
                    personalFinanceDocuments {
                      ...PersonalFinanceDocumentField
                      __typename
                    }
                    __typename
                  }
                  __typename
                }
                delivery {
                  ...DeliveryDetailsFields
                  __typename
                }
                manufacturing {
                  ...ManufacuringFields
                  __typename
                }
                financialContract {
                  ...FinancialContractFields
                  __typename
                }
                acceptedHandover {
                  startTime
                  endTime
                  timeZone
                  __typename
                }
                handoverBooking {
                  handoverLocation {
                    ...HandoverLocationFields
                    __typename
                  }
                  suggestedHandoverTimes {
                    ...SuggestHandoverTimesFields
                    __typename
                  }
                  handoverSuggestedAt
                  method
                  __typename
                }
                registrationInformation {
                  hasMarketCitizenship
                  hasPreviousRegistrationCertificate
                  towBarInspection
                  transferLicensePlate
                  useRegistrationAgency
                  hasLockedRegistrationInformation
                  hasSelectedRegistrationType
                  registrationInformationHasBeenRead
                  documents {
                    ...RegistrationDocumentFields
                    __typename
                  }
                  registeredOwners {
                    ...RegisteredOwnerFields
                    __typename
                  }
                  possessors {
                    name
                    ssn
                    __typename
                  }
                  garageAddress {
                    ...GarageAddressFields
                    __typename
                  }
                  additionalInformation {
                    ...AdditionalRegistrationInformationFields
                    __typename
                  }
                  __typename
                }
                insuranceInformation {
                  ...InsuranceInformationFields
                  __typename
                }
                paymentAdvise {
                  ...DocumentFields
                  __typename
                }
                salesContract {
                  ...DocumentFields
                  __typename
                }
                salesInvoice {
                  ...DocumentFields
                  __typename
                }
                registrationCertificate {
                  ...DocumentFields
                  __typename
                }
                deliveryAcceptance {
                  ...DocumentFields
                  __typename
                }
                allInvoices {
                  ...InvoiceDocumentFields
                  __typename
                }
                orderContent {
                  expectedDeliveryDate
                  specifications {
                    ...SpecificationSummaryFields
                    __typename
                  }
                  media {
                    ...MediaFields
                    __typename
                  }
                  extras {
                    ...ExtrasFields
                    __typename
                  }
                  configuration {
                    modelYear
                    __typename
                  }
                  __typename
                }
                payment {
                  amountPaid
                  cashAmountPaid
                  creditAmountPaid
                  homeDeliveryAmount
                  homeDeliveryAmountWithoutVat
                  amountReserved
                  status
                  invoices {
                    ...DocumentFields
                    __typename
                  }
                  paymentOption
                  __typename
                }
                paymentInformation {
                  bank
                  accountNumber
                  accountType
                  customerName
                  dueDate
                  __typename
                }
                orderConfirmedAt
                remarketingOrderConfirmation {
                  ...DocumentFields
                  __typename
                }
                salesContractSigners {
                  firstName
                  lastName
                  email
                  birthDate
                  ssn
                  phoneNumber
                  isConsumer
                  __typename
                }
                ordererPolestarIds
                driverPolestarIds
                price {
                  car {
                    ...CarPriceFields
                    __typename
                  }
                  extras {
                    ...ExtraPriceFields
                    __typename
                  }
                  totals {
                    ...TotalsFields
                    __typename
                  }
                  __typename
                }
                paymentDueDate
                salesContractDeadline
                remarketedCar {
                  ...RemarketedCarFields
                  __typename
                }
                tireDetails {
                  ...TireDetailsFields
                  __typename
                }
                market
                isFsaFinancing
                isPriceIncorrect
                __typename
              }
            }
                            
            fragment TireDetailsFields on TireDetails {
              selectedTireHotel {
                ...TireHotelFields
                __typename
              }
              mountWinterWheels
              useTireHotel
              customerHasSetTireDetails
              availableTireHotels {
                ...RelativelyLocatedTireHotelFields
                __typename
              }
              __typename
            }
                            
            fragment TireHotelFields on TireHotel {
              locationCode
              name
              address
              postalCode
              city
              phone
              __typename
            }
                            
            fragment RelativelyLocatedTireHotelFields on RelativelyLocatedTireHotel {
              tireHotel {
                ...TireHotelFields
                __typename
              }
              distanceMeters
              index
              __typename
            }
                            
            fragment RemarketedCarFields on RemarketedCar {
              mileage
              numberOfPreviousOwners
              firstDayInTraffic
              firstRegDate
              factoryCompleteDate
              hasWinterWheels
              hasTowbar
              isVatDeductible
              bankDetails {
                ...BankDetailsFields
                __typename
              }
              investor {
                ...InvestorFields
                __typename
              }
              __typename
            }
                            
            fragment InvestorFields on Investor {
              investorId
              vehicleAdId
              name
              orgNumber
              address
              email
              homeDeliveryAvailable
              __typename
            }
                            
            fragment BankDetailsFields on BankDetails {
              accountNumber
              bank
              company
              bankSortCode
              swift
              iban
              __typename
            }
                            
            fragment ExtraPriceFields on ExtraPrice {
              extraPriceId: id
              price
              vat
              priceIncVat
              __typename
            }
                            
            fragment CarPriceFields on CarPrice {
              code
              price
              vat
              priceIncVat
              type
              __typename
            }
                            
            fragment TotalsFields on Totals {
              carBreakdown {
                ...CarPriceBreakdownFields
                __typename
              }
              deliveryPriceBreakdown {
                ...DeliveryPriceBreakdownFields
                __typename
              }
              discountPriceBreakdown {
                ...DiscountPriceBreakdownFields
                __typename
              }
              priceWithDiscountBreakdown {
                ...PriceWithDiscountBreakdownFields
                __typename
              }
              grandTotalPriceWithDiscountBreakdown {
                ...GrandTotalPriceWithDiscountBreakdownFields
                __typename
              }
              grandTotal {
                ...GrandTotalFields
                __typename
              }
              __typename
            }
                            
            fragment CarPriceBreakdownFields on CarPriceBreakdown {
              carTotalPrice {
                ...PriceElementFields
                __typename
              }
              carTotalBasicPrice {
                ...PriceElementFields
                __typename
              }
              carTotalTaxes {
                ...PriceElementFields
                __typename
              }
              carTotalVat {
                ...PriceElementFields
                __typename
              }
              carTotalPriceExclVat {
                ...PriceElementFields
                __typename
              }
              carTotalIncentive {
                ...PriceElementFields
                __typename
              }
              carTotalIncentiveExclVat {
                ...PriceElementFields
                __typename
              }
              carTotalPriceExclIncentive {
                ...PriceElementFields
                __typename
              }
              carTotalPriceExclIncentiveExclVat {
                ...PriceElementFields
                __typename
              }
              uraxTaxes {
                ...TaxFields
                __typename
              }
              __typename
            }
                            
            fragment TaxFields on Tax {
              taxId
              name
              amount
              __typename
            }
                            
            fragment DeliveryPriceBreakdownFields on DeliveryPriceBreakdown {
              deliveryChargePrice {
                ...PriceElementFields
                __typename
              }
              deliveryChargeBasicPrice {
                ...PriceElementFields
                __typename
              }
              deliveryChargeVat {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment DiscountPriceBreakdownFields on DiscountPriceBreakdown {
              discountTotalPrice {
                ...PriceElementFields
                __typename
              }
              discountBasicPrice {
                ...PriceElementFields
                __typename
              }
              discountPriceExclVat {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment PriceWithDiscountBreakdownFields on PriceWithDiscountBreakdown {
              carTotalPriceWithDiscountBasicPrice {
                ...PriceElementFields
                __typename
              }
              carTotalPriceWithDiscount {
                ...PriceElementFields
                __typename
              }
              carTotalPriceWithDiscountExclVat {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment GrandTotalPriceWithDiscountBreakdownFields on GrandTotalPriceWithDiscountBreakdown {
              grandTotalCarExtrasDeliveryPriceWithDiscount {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryPriceBeforeVatWithDiscount {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryVatWithDiscount {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment GrandTotalFields on GrandTotal {
              grandTotalCarExtras {
                ...GrandTotalCarExtrasFields
                __typename
              }
              grandTotalCarExtrasDelivery {
                ...GrandTotalCarExtrasDeliveryFields
                __typename
              }
              __typename
            }
                            
            fragment GrandTotalCarExtrasFields on GrandTotalCarExtras {
              grandTotalCarExtrasPrice {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasBeforeVat {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasExclIncentive {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasExclIncentiveBeforeVat {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment GrandTotalCarExtrasDeliveryFields on GrandTotalCarExtrasDelivery {
              grandTotalCarExtrasDeliveryPrice {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryBeforeVat {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryVat {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryExclIncentive {
                ...PriceElementFields
                __typename
              }
              grandTotalCarExtrasDeliveryExclIncentiveBeforeVat {
                ...PriceElementFields
                __typename
              }
              __typename
            }
                            
            fragment PriceElementFields on PriceElement {
              priceId: id
              label
              value
              __typename
            }
                            
            fragment InsuranceInformationFields on InsuranceInformation {
              insuranceType
              hasPersonalInsurance
              hasPolestarInsurance
              hasInsuranceInformation
              informationSource
              contractReferenceNumber
              insuranceOption
              insuranceProvider
              insurancePolicyHolder
              insuranceCode
              otherInformation
              insuranceBroker {
                ...InsuranceBrokerFields
                __typename
              }
              insuranceCertificateDocument {
                ...DocHubDocument
                __typename
              }
              insuranceInformationHasBeenRead
              __typename
            }
                            
            fragment DepositInformationFields on DepositInformation {
              amount
              vatAmount
              currency
              vatName
              __typename
            }
                            
            fragment ExtrasFields on Extra {
              salesExtraId
              articleNumber
              name
              quantity
              price
              vat
              __typename
            }
                            
            fragment MediaFields on Media {
              exteriorImages {
                ...ImageFields
                __typename
              }
              interiorImages {
                ...ImageFields
                __typename
              }
              __typename
            }
                            
            fragment ImageFields on Image {
              size
              url
              __typename
            }
                            
            fragment SpecificationSummaryFields on SpecificationSummary {
              lines {
                ...SpecificationLineFields
                __typename
              }
              specifications {
                ...LabelValueFields
                __typename
              }
              dimensions {
                ...LabelValueFields
                __typename
              }
              __typename
            }
                            
            fragment SpecificationLineFields on SpecificationLine {
              category
              label
              value
              code
              media
              price
              quantity
              __typename
            }
                            
            fragment LabelValueFields on LabelValue {
              label
              value
              __typename
            }
                            
            fragment InvoiceDocumentFields on InvoiceDocument {
              isComplete
              documentId
              documentDisplayName
              documentFullName
              isCreditInvoice
              created
              uniqueFileReference
              requestIdForCreditedInvoice
              requestId
              __typename
            }
                            
            fragment DriverFields on Driver {
              firstName
              lastName
              email
              phoneNumber
              address
              careOf
              city
              postalCode
              marketingConsent
              driverInformationHasBeenRead
              __typename
            }
                            
            fragment ConsumerFields on MyO2ConsumerDetails {
              consumerType
              consumerSubType
              firstName
              lastName
              email
              phoneNumber
              address
              careOf
              city
              postalCode
              region
              birthDate
              ssn
              companyName
              vatNumber
              organizationNumber
              billingPostalCode
              billingCity
              billingAddress
              billingCareOf
              billingCountry
              billingRegion
              hasGivenConsentToShareInformationWithDelwpForZeroEmissionProgram
              agreementCodes
              __typename
            }
                            
            fragment ManufacuringFields on Manufacturing {
              chassisNumber
              manufacturingMonth
              registrationNumber
              vehicleIdentificationNumber
              mileage
              registrationDate
              vehicleTypeCertificateNumber
              vehicleSerialNumber
              __typename
            }
                            
            fragment HandoverLocationFields on HandoverLocation {
              name
              addressLine
              zipCode
              city
              countryCode
              latitude
              longitude
              handoverLocationId
              __typename
            }
                            
            fragment StateFields on States {
              isLocked
              isCancelled
              salesContractRequested
              salesContractReceived
              hasAcceptedHandover
              hasInvoice
              hasPendingInvoiceRequest
              hasCarDelivered
              additionalFinanceConfirmationRequired
              remarketingOrderConfirmationReceived
              remarketingOrderConfirmationRequested
              hasBeenLockedAtLeastOnce
              hasCarArrivedAtDealer
              isSubsidyAllocated
              hasSubsidyHadAnyStatusAtLeastOnce
              hasChangedFinancing
              hasChangedExtras
              hasChangedConsumerType
              __typename
            }
                            
            fragment RegistrationDocumentFields on RegistrationDocument {
              type
              status
              uniqueFileReference
              fileName
              documentId
              documentSource
              documentFullName
              __typename
            }
                            
            fragment PersonalFinanceApplicationFields on MyO2PersonalFinanceApplication {
              financingStatus
              leaseProvider
              leaseProviderId
              leaseDownPayment
              loanProvider
              loanProviderId
              loanFinancedAmount
              approvedBeforeSoftLock
              numberOfFinancingReviews
              loanOwnFinancingViaBankAccount
              personalNumber
              isCashButPresentedAsSelfFinancing
              __typename
            }
                            
            fragment PersonalFinanceDocumentField on MyO2PersonalFinanceDocument {
              status
              uniqueFileReference
              fileName
              documentId
              documentSource
              documentFullName
              __typename
            }
                            
            fragment DeliveryDetailsFields on DeliveryDetails {
              bookedDate
              bookedDateTimeZoneId
              latestDateToLockOrder
              deliveredDetails {
                ...DeliveredDetailsFields
                __typename
              }
              customerEarliestPossibleHandoverDate
              insuranceDeadline
              registrationDeadline
              carHasArrivedAtDealer
              isQuickProcess
              estimatedDeliveryDate
              __typename
            }
                            
            fragment DeliveredDetailsFields on DeliveredDetails {
              handoverCompletedAt
              mileage
              signedDocumentReference
              __typename
            }
                            
            fragment FinanceApplicationFields on MyO2FinanceApplication {
              financialApplicationId
              externalReferencingId
              financingStatus
              allowUpdates
              allowRevisits
              isCreditCheck
              termMonths
              downPayment
              monthlyCost
              interestRate
              effectiveInterestRate
              annualMileage
              beneficialOwnershipInformationRequired
              residualValue
              balloonAmount
              includesBalloon
              isFinancialLease
              priceDetails
              additionalInformationFinePrint
              __typename
            }
                            
            fragment InsuranceBrokerFields on InsuranceBroker {
              name
              email
              phoneNumber
              __typename
            }
                            
            fragment SuggestHandoverTimesFields on SuggestedHandoverTimes {
              start
              end
              timezone
              __typename
            }
                            
            fragment FinancialContractFields on FinancialContract {
              status
              signatories
              branchNumber
              __typename
            }
                            
            fragment DocumentFields on MyO2PDFDocument {
              fileName
              documentId
              __typename
            }
                            
            fragment FileRefenceFields on MyO2FileReference {
              bucket
              path
              documentType
              __typename
            }
                            
            fragment AdditionalInformationFields on MyO2AdditionalInformation {
              key
              value
              __typename
            }
                            
            fragment IdentifierFields on MyO2Identifier {
              name
              identifier
              __typename
            }
                            
            fragment RegisteredOwnerFields on RegisteredOwner {
              name
              identifier
              gender
              dateOfBirth
              emailAddress
              phoneNumber
              driversLicenseNumber
              driversLicenseExpiryDate
              businessRegistrationCustomerNumber
              residentialAddress {
                ...RegistrationAddressFields
                __typename
              }
              mailingAddress {
                ...RegistrationAddressFields
                __typename
              }
              __typename
            }
                            
            fragment RegistrationAddressFields on RegistrationAddress {
              addressLine1
              addressLine2
              addressLine3
              suburb
              state
              postalCode
              __typename
            }
                            
            fragment DocHubDocument on MyO2DocHubDocument {
              documentType
              status
              uniqueFileReference
              fileName
              documentId
              documentSource
              documentFullName
              __typename
            }
                            
            fragment GarageAddressFields on GarageAddress {
              addressLine1
              addressLine2
              addressLine3
              suburb
              state
              postalCode
              typeOfUse
              __typename
            }
                            
            fragment AdditionalRegistrationInformationFields on AdditionalRegistrationInformation {
              dateOfBirth
              placeOfBirth
              choiceOfDeclaration
              __typename
            }
            """;

    public static final String GET_ACCESS_TOKEN_OPERATION = "getAuthToken";
    public static final String GET_ACCESS_TOKEN_QUERY = """
            query getAuthToken($code: String!) {
              getAuthToken(code: $code) {
                id_token
                access_token
                refresh_token
                expires_in
              }
            }
            """;
}
