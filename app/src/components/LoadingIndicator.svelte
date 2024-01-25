<div class="loadingIndicator">
	<div class="dot-elastic" />
</div>

<style lang="scss">
	@mixin keyframes($name) {
		@keyframes #{$name} {
			@content;
		}
	}

	@mixin animate($delay) {
		animation: loadingAnimation 1s infinite ease-in-out;
		animation-delay: $delay;
	}

	.loadingIndicator {
		width: 150px;
		display: flex;
		justify-content: space-around;
		align-items: center;

		.dot-elastic {
			position: relative;
			width: 20px;
			height: 20px;
			border-radius: 50%;
			background-color: var(--mainColor);
			color: var(--mainColor);
			animation: dot-elastic 1s infinite linear;

			&::before,
			&::after {
				content: '';
				display: inline-block;
				position: absolute;
				top: 0;
			}
			&::before {
				left: -30px;
				width: 20px;
				height: 20px;
				border-radius: 50%;
				background-color: var(--mainColor);
				color: var(--mainColor);
				animation: dot-elastic-before 1s infinite linear;
			}
			&::after {
				left: 30px;
				width: 20px;
				height: 20px;
				border-radius: 50%;
				background-color: var(--mainColor);
				color: var(--mainColor);
				@include animate(2.8s);
				animation: dot-elastic-after 1s infinite linear;
			}
		}

		@include keyframes(dot-elastic-before) {
			0% {
				transform: scale(1, 1);
			}
			25% {
				transform: scale(1, 1.7);
			}
			50% {
				transform: scale(1, 0.67);
			}
			75% {
				transform: scale(1, 1);
			}
			100% {
				transform: scale(1, 1);
			}
		}
		@include keyframes(dot-elastic) {
			0% {
				transform: scale(1, 1);
			}
			25% {
				transform: scale(1, 1);
			}
			50% {
				transform: scale(1, 1.7);
			}
			75% {
				transform: scale(1, 1);
			}
			100% {
				transform: scale(1, 1);
			}
		}
		@include keyframes(dot-elastic-after) {
			0% {
				transform: scale(1, 1);
			}
			25% {
				transform: scale(1, 1);
			}
			50% {
				transform: scale(1, 0.67);
			}
			75% {
				transform: scale(1, 1.7);
			}
			100% {
				transform: scale(1, 1);
			}
		}
	}
</style>
