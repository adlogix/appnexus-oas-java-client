package eu.adlogix.appnexus.oas.client.util;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.enums.BillTo;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignStatus;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignType;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignsBy;
import eu.adlogix.appnexus.oas.client.domain.enums.Completion;
import eu.adlogix.appnexus.oas.client.domain.enums.DayOfWeek;
import eu.adlogix.appnexus.oas.client.domain.enums.FrequencyScope;
import eu.adlogix.appnexus.oas.client.domain.enums.HourOfDay;
import eu.adlogix.appnexus.oas.client.domain.enums.InsertionOrderStatus;
import eu.adlogix.appnexus.oas.client.domain.enums.PaymentMethod;
import eu.adlogix.appnexus.oas.client.domain.enums.Reach;
import eu.adlogix.appnexus.oas.client.domain.enums.SegmentType;
import eu.adlogix.appnexus.oas.client.domain.enums.SmoothAsap;
import eu.adlogix.appnexus.oas.client.domain.enums.XmlBoolean;
import static org.testng.Assert.assertEquals;

public class EnumUtilsTest {
	@Test
	public void fromString_ValidBillToCode_FindsCorrectCode() {
		assertEquals(BillTo.ADVERTISER, BillTo.fromString("A"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid BillTo Code 'J'. Valid values are \\[A, G\\]")
	public void fromString_InvalidBillToCode_ThrowsException() {
		BillTo.fromString("J");
	}

	@Test
	public void fromString_ValidCampaignsByCode_FindsCorrectCode() {
		assertEquals(CampaignsBy.ADVERTISER, CampaignsBy.fromString("A"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid CampaignsBy Code 'J'. Valid values are \\[A, G\\]")
	public void fromString_InvalidCampaignsByCode_ThrowsException() {
		CampaignsBy.fromString("J");
	}

	@Test
	public void fromString_ValidCampaignStatusCode_FindsCorrectCode() {
		assertEquals(CampaignStatus.WORK_IN_PROGRESS, CampaignStatus.fromString("W"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid CampaignStatus Code 'NO'. Valid values are \\[W, L, P, O, C, R, J, X, S, A, D, T\\]")
	public void fromString_InvalidCampaignStatusCode_ThrowsException() {
		CampaignStatus.fromString("NO");
	}

	@Test
	public void fromString_ValidCampaignTypeCode_FindsCorrectCode() {
		assertEquals(CampaignType.CLT, CampaignType.fromString("CLT"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid CampaignType Code 'J'. Valid values are \\[CLT, RM\\]")
	public void fromString_InvalidCampaignTypeCode_ThrowsException() {
		CampaignType.fromString("J");
	}

	@Test
	public void fromString_ValidCompletionCode_FindsCorrectCode() {
		assertEquals(Completion.SOONEST, Completion.fromString("S"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid Completion Code 'J'. Valid values are \\[S, L, E, I, C, U\\]")
	public void fromString_InvalidCompletioneCode_ThrowsException() {
		Completion.fromString("J");
	}

	@Test
	public void fromString_ValidDayOfWeekCode_FindsCorrectCode() {
		assertEquals(DayOfWeek.SUNDAY, DayOfWeek.fromString("0"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid DayOfWeek Code 'J'. Valid values are \\[0, 1, 2, 3, 4, 5, 6\\]")
	public void fromString_InvalidDayOfWeekCode_ThrowsException() {
		DayOfWeek.fromString("J");
	}

	@Test
	public void fromString_ValidFrequencyScopeCode_FindsCorrectCode() {
		assertEquals(FrequencyScope.ZERO, FrequencyScope.fromString("0"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid FrequencyScope Code 'J'. Valid values are \\[0, 1, 2, 3, 4, 5, 6\\]")
	public void fromString_InvalidFrequencyScopeCode_ThrowsException() {
		FrequencyScope.fromString("J");
	}

	@Test
	public void fromString_ValidHourOfDayCode_FindsCorrectCode() {
		assertEquals(HourOfDay.ZERO, HourOfDay.fromString("00"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid HourOfDay Code 'J'. Valid values are \\[00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23\\]")
	public void fromString_InvalidHourOfDayCode_ThrowsException() {
		HourOfDay.fromString("J");
	}

	@Test
	public void fromString_ValidInsertionOrderStatusCode_FindsCorrectCode() {
		assertEquals(InsertionOrderStatus.APPROVED, InsertionOrderStatus.fromString("A"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid InsertionOrderStatus Code 'J'. Valid values are \\[A, P\\]")
	public void fromString_InvalidInsertionOrderStatusCode_ThrowsException() {
		InsertionOrderStatus.fromString("J");
	}

	@Test
	public void fromString_ValidPaymentMethodCode_FindsCorrectCode() {
		assertEquals(PaymentMethod.CASH, PaymentMethod.fromString("C"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid PaymentMethod Code 'J'. Valid values are \\[C, B, I, T\\]")
	public void fromString_InvalidPaymentMethodCode_ThrowsException() {
		PaymentMethod.fromString("J");
	}

	@Test
	public void fromString_ValidReachCode_FindsCorrectCode() {
		assertEquals(Reach.OPEN, Reach.fromString("O"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid Reach Code 'J'. Valid values are \\[O, F, D, M, H\\]")
	public void fromString_InvalidReachCode_ThrowsException() {
		Reach.fromString("J");
	}

	@Test
	public void fromString_ValidSegmentTypeCode_FindsCorrectCode() {
		assertEquals(SegmentType.ANY, SegmentType.fromString("A"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid SegmentType Code 'J'. Valid values are \\[A, L\\]")
	public void fromString_InvalidSegmentTypeCode_ThrowsException() {
		SegmentType.fromString("J");
	}

	@Test
	public void fromString_ValidSmoothAsapCode_FindsCorrectCode() {
		assertEquals(SmoothAsap.SMOOTH, SmoothAsap.fromString("S"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid SmoothAsap Code 'J'. Valid values are \\[S, A\\]")
	public void fromString_InvalidSmoothAsapCode_ThrowsException() {
		SmoothAsap.fromString("J");
	}

	@Test
	public void fromString_ValidXmlBooleanCode_FindsCorrectCode() {
		assertEquals(XmlBoolean.Y, XmlBoolean.fromString("Y"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Invalid XmlBoolean Code 'J'. Valid values are \\[Y, N\\]")
	public void fromString_InvalidXmlBooleanCode_ThrowsException() {
		XmlBoolean.fromString("J");
	}
}
