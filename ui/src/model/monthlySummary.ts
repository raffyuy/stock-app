export interface MonthlySummary {
  month: string;
  averageMonthlyRating: number;
  headChange: number;
  recordCount: number;
  finalReadings: {
    head: number;
    weight: number;
    price: number;
  }
}

export interface Summaries {
  summaries: MonthlySummary[];
}