export default function getColorOfLanguage(lang: string): string {
    switch (lang) {
        case "py":
            return "#2b5b84"
        default:
            return "#fff"
    }
}