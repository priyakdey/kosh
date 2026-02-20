package com.priyakdey.backend.model.response;

import java.util.List;

public record CreditCardsResponse(
        Summary summary,
        List<Card> cards,
        List<Utilization> utilization,
        List<TimelineEvent> timeline,
        List<RecentActivity> recentActivity
) {

    public record Summary(int totalOutstanding, int totalCreditLimit,
                          double utilizationPercent, int activeCards,
                          int dueIn7Days, int minDue) {
    }

    public record Card(
            String id, String bankName, String cardVariant, String network,
            String last4,
            int outstanding,
            int creditLimit,
            String dueDate,
            String billingDate,
            String themeId
    ) {
    }

    public record Utilization(
            String cardId,
            String label,
            int percent
    ) {
    }

    public record TimelineEvent(
            String cardId,
            String date,
            String label,
            int amount,
            String severity
    ) {
    }

    public record RecentActivity(
            String id,
            String date,
            String cardLabel,
            String merchant,
            int amount
    ) {
    }
}
