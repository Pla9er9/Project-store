<div class="spinner"></div>

<style lang="scss">
    .spinner {
        width: 50px;
        height: 50px;
        border-radius: 25px;
        border-left: solid 3px var(--mainColor);
        animation: spin 1s infinite linear;
    }

    @mixin keyframes($name) {
        @keyframes #{$name} {
            @content;
        }
    }

    @include keyframes(spin) {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
</style>
